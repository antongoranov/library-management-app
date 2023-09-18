package bg.sfa_library_management.web;

import bg.sfa_library_management.model.dtos.binding.AuthorBindingDTO;
import bg.sfa_library_management.model.dtos.view.AuthorViewDTO;
import bg.sfa_library_management.service.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorServiceImpl authorService;

    @Autowired
    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorViewDTO>> allAuthors() throws SQLException {
        List<AuthorViewDTO> fetchedAuthors = this.authorService.getAllAuthors();

        return ResponseEntity.ok(fetchedAuthors);
    }

    public ResponseEntity<AuthorViewDTO> getAuthorById(@PathVariable long id) throws SQLException {

        Optional<AuthorViewDTO> optAuthorDto = this.authorService.findById(id);

        if (optAuthorDto.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(optAuthorDto.get());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNewAuthor(@Valid @RequestBody AuthorBindingDTO authorDTO,
                                             BindingResult bindingResult,
                                             UriComponentsBuilder uriComponentsBuilder) throws SQLException {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult
                            .getFieldErrors()
                            .get(0)
                            .getDefaultMessage()
            );
        }

        AuthorViewDTO createdAuthor = this.authorService.createNewAuthor(authorDTO);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/authors/{id}")
                        .build(createdAuthor.getId()))
                .build();
    }
}












