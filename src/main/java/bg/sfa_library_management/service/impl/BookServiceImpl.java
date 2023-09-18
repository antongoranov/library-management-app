package bg.sfa_library_management.service.impl;

import bg.sfa_library_management.dao.BookDAO;
import bg.sfa_library_management.model.dtos.binding.BookBindingDTO;
import bg.sfa_library_management.model.dtos.view.BookViewDTO;
import bg.sfa_library_management.model.entity.Book;
import bg.sfa_library_management.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO,
                           ModelMapper modelMapper) {
        this.bookDAO = bookDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookViewDTO> getAllBooks() throws SQLException {

        List<Book> books = this.bookDAO.findAllBooks();

        return books
                .stream()
                .map(this::bookToDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookViewDTO> getBookByID(long id) throws SQLException {

        return this.bookDAO.findBookById(id)
                .map(this::bookToDTOMapper);
    }

    @Override
    public BookViewDTO createNewBook(BookBindingDTO bookBindingDTO) throws SQLException {

        Book createdBook = this.bookDAO.createBook(bookBindingDTO);

        return bookToDTOMapper(createdBook);
    }

    private BookViewDTO bookToDTOMapper(Book book) {
        return this.modelMapper.map(book, BookViewDTO.class);
    }
}
