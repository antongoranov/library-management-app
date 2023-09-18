package bg.sfa_library_management.service;

import bg.sfa_library_management.model.dtos.binding.AuthorBindingDTO;
import bg.sfa_library_management.model.dtos.view.AuthorViewDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorViewDTO> getAllAuthors() throws SQLException;
    public Optional<AuthorViewDTO> findById(long id) throws SQLException;

    AuthorViewDTO createNewAuthor(AuthorBindingDTO authorDTO) throws SQLException;
}
