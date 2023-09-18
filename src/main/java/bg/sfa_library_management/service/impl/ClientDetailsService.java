package bg.sfa_library_management.service.impl;

import bg.sfa_library_management.dao.ClientDAO;
import bg.sfa_library_management.model.entity.Client;
import bg.sfa_library_management.model.entity.Role;
import bg.sfa_library_management.model.security.ClientUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

public class ClientDetailsService implements UserDetailsService {

    private final ClientDAO clientDao;

    public ClientDetailsService(ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //TODO: check the sql exception thrown
        try {
            return this.clientDao.findClientByUsername(username)
                    .map(this::mapToUserDetails)
                    .orElseThrow(() -> new EntityNotFoundException("Entity does not exist!"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserDetails mapToUserDetails(Client client) {
        return new ClientUserDetails(client.getId(),
                client.getFullName(),
                client.getUsername(),
                client.getPassword(),
                client.getEmail(),
                client.getRoles()
                        .stream()
                        .map(this::mapRolesToGrantedAuthority)
                        .toList());
    }

    private GrantedAuthority mapRolesToGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRole().name());
    }
}
