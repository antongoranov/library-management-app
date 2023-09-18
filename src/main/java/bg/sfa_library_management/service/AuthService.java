package bg.sfa_library_management.service;


import bg.sfa_library_management.model.dtos.binding.ClientRegistrationDTO;
import bg.sfa_library_management.model.dtos.view.ClientViewDTO;

import java.sql.SQLException;

public interface AuthService {
    void authenticateUser(String username, String password);

    ClientViewDTO register(ClientRegistrationDTO clientRegistrationDTO) throws SQLException;
}
