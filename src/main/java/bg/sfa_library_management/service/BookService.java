package bg.sfa_library_management.service;

import bg.sfa_library_management.model.dtos.binding.BookBindingDTO;
import bg.sfa_library_management.model.dtos.view.BookViewDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookViewDTO> getAllBooks() throws SQLException;

    Optional<BookViewDTO> getBookByID(long id) throws SQLException;

    BookViewDTO createNewBook(BookBindingDTO bookBindingDTO) throws SQLException;
}
