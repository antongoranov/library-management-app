package bg.sfa_library_management.dao;

import bg.sfa_library_management.model.dtos.binding.ClientRegistrationDTO;
import bg.sfa_library_management.model.entity.Client;
import bg.sfa_library_management.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ClientDAO {

    private final DataSource dataSource;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientDAO(DataSource dataSource, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Client> findClientById(long clientId) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, email, full_name, password, username FROM clients WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, clientId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client createdClient = createClient(rs);
                stmt.close();
                return Optional.of(createdClient);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }
    }

    public Optional<Client> findClientByUsername(String username) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, email, full_name, password, username FROM clients WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client createdClient = createClient(rs);
                stmt.close();
                return Optional.of(createdClient);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }
    }

    public Optional<Client> findClientByEmail(String email) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, email, full_name, password, username FROM clients WHERE email=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client createdClient = createClient(rs);
                stmt.close();
                return Optional.of(createdClient);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }
    }

    public Client registerClient(ClientRegistrationDTO clientRegistrationDTO) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {

            String sql = "INSERT INTO clients(username, password, full_name, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, clientRegistrationDTO.getUsername());

            String encodedPassword = passwordEncoder.encode(clientRegistrationDTO.getPassword());
            stmt.setString(2, encodedPassword);

            stmt.setString(3, clientRegistrationDTO.getFullName());
            stmt.setString(4, clientRegistrationDTO.getEmail());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            long id;
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                this.roleDAO.assignClientRole(id);
                Set<Role> clientRoles = this.roleDAO.getClientRoles(id);
                stmt.close();

                return new Client(
                        id,
                        clientRegistrationDTO.getFullName(),
                        clientRegistrationDTO.getUsername(),
                        encodedPassword,
                        clientRegistrationDTO.getEmail(),
                        clientRoles);
            } else {
                throw new SQLException("Registration failed. No ID obtained");
            }
        }
    }

    public List<Client> findAllClients() throws SQLException {

        try(Connection conn = dataSource.getConnection()){
            List<Client> clients = new ArrayList<>();

            String sql = "SELECT id, username, password, full_name, email FROM clients";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Client client = createClient(rs);
                clients.add(client);
            }

            stmt.close();
            return clients;
        }
    }

    private Client createClient(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String fullName = rs.getString("full_name");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        Set<Role> roles = this.roleDAO.getClientRoles(rs.getLong(1));

        return new Client(id, fullName, username, password, email, roles);
    }
}
