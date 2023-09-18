package bg.sfa_library_management.dao;

import bg.sfa_library_management.model.entity.Role;
import bg.sfa_library_management.model.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoleDAO {

    private final DataSource dataSource;

    @Autowired
    public RoleDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Set<Role> getClientRoles(long clientId) throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            Set<Role> clientRoles = new HashSet<>();

            String sql = "SELECT id, role FROM roles " +
                         "JOIN clients_roles ON roles.id = clients_roles.roles_id " +
                         "WHERE clients_roles.client_id=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                long roleId = rs.getLong("id");
                String roleName = rs.getString("role");

                Role role = new Role(roleId, RoleEnum.valueOf(roleName));

                clientRoles.add(role);
            }
            stmt.close();
            return clientRoles;
        }
    }

    public void assignClientRole(long clientId) throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO clients_roles(client_id, roles_id) VALUES (?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, clientId);
            //assigning USER role to a newly registered Client
            stmt.setLong(2, 3);
            stmt.executeUpdate();

            stmt.close();
        }

    }
}
