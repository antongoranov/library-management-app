package bg.sfa_library_management.service.impl;

import bg.sfa_library_management.dao.ClientDAO;
import bg.sfa_library_management.exception.PasswordsDoNotMatchException;
import bg.sfa_library_management.model.dtos.binding.ClientRegistrationDTO;
import bg.sfa_library_management.model.dtos.view.ClientViewDTO;
import bg.sfa_library_management.model.entity.Client;
import bg.sfa_library_management.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final ClientDAO clientDAO;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthServiceImpl(UserDetailsService userDetailsService,
                           ClientDAO clientDAO,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userDetailsService = userDetailsService;
        this.clientDAO = clientDAO;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    //if login is unsuccessful throws an exception
    @Override
    public void authenticateUser(String username, String rawPassword) throws EntityNotFoundException, PasswordsDoNotMatchException {

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new PasswordsDoNotMatchException("Incorrect Password!");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public ClientViewDTO register(ClientRegistrationDTO clientRegistrationDTO) throws SQLException {
        String clientEmail = clientRegistrationDTO.getEmail();

        clientDAO.findClientByEmail(clientEmail)
                .ifPresent(client -> {
                    throw new EntityExistsException("Client with email " + clientEmail + " already exists");
                });

        Client registeredClient = this.clientDAO.registerClient(clientRegistrationDTO);

        return this.modelMapper.map(registeredClient, ClientViewDTO.class);
    }

}
