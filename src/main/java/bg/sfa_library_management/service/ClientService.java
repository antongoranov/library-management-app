package bg.sfa_library_management.service;

import bg.sfa_library_management.model.dtos.view.AuthorViewDTO;
import bg.sfa_library_management.model.dtos.view.ClientViewDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientViewDTO> getAllClients() throws SQLException;

    Optional<ClientViewDTO> findById(long id) throws SQLException;
}
