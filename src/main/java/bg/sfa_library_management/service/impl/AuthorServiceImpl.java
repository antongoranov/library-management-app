package bg.sfa_library_management.service.impl;

import bg.sfa_library_management.dao.AuthorDAO;
import bg.sfa_library_management.model.dtos.binding.AuthorBindingDTO;
import bg.sfa_library_management.model.dtos.view.AuthorViewDTO;
import bg.sfa_library_management.model.entity.Author;
import bg.sfa_library_management.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    private final ModelMapper mapper;

    @Autowired
    public AuthorServiceImpl(AuthorDAO authorDAO,
                             ModelMapper mapper) {

        this.authorDAO = authorDAO;

        this.mapper = mapper;
    }

    @Override
    public List<AuthorViewDTO> getAllAuthors() throws SQLException {

        List<Author> fetchedAuthors = this.authorDAO.findAllAuthors();

       return fetchedAuthors
                .stream()
                .map(this::authorToDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorViewDTO> findById(long id) throws SQLException {

        return this.authorDAO.findAuthorById(id)
                .map(this::authorToDTOMapper);
    }

    @Override
    public AuthorViewDTO createNewAuthor(AuthorBindingDTO authorDTO) throws SQLException {

        Author createdAuthor = this.authorDAO.createAuthor(authorDTO);

        return authorToDTOMapper(createdAuthor);
    }

    private AuthorViewDTO authorToDTOMapper(Author author) {
        return this.mapper.map(author, AuthorViewDTO.class);
    }
}
