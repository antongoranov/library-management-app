package bg.sfa_library_management.web;

import bg.sfa_library_management.model.dtos.binding.BookBindingDTO;
import bg.sfa_library_management.model.dtos.view.BookViewDTO;
import bg.sfa_library_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.Binding;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookViewDTO>> allBooks() throws SQLException {
        List<BookViewDTO> allBooks = this.bookService.getAllBooks();

        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookViewDTO> bookById(@PathVariable("id") long id) throws SQLException {

        Optional<BookViewDTO> bookByIdDTO = this.bookService.getBookByID(id);

        if (bookByIdDTO.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(bookByIdDTO.get());
    }

    @PostMapping
    public ResponseEntity<Void> addNewBook(@Valid @RequestBody BookBindingDTO bookBindingDTO,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws SQLException {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(
                    bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(fieldError -> fieldError.getDefaultMessage())
                            .collect(Collectors.joining("\n")));
        }

        BookViewDTO createdBook = bookService.createNewBook(bookBindingDTO);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/books/{id}")
                        .build(createdBook.getId()))
                .build();

    }
}
