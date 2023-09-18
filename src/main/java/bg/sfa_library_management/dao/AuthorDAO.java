package bg.sfa_library_management.dao;

import bg.sfa_library_management.model.dtos.binding.AuthorBindingDTO;
import bg.sfa_library_management.model.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAO {

    private final DataSource dataSource;

    @Autowired
    public AuthorDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //GET all Authors
    public List<Author> findAllAuthors() throws SQLException {

        try(Connection conn = dataSource.getConnection()){
            List<Author> authors = new ArrayList<>();

            String sql = "SELECT id, name FROM authors";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                long id = rs.getLong("id");
                String authorName = rs.getString("name");

                Author retrievedAuthor = new Author(id, authorName);

                authors.add(retrievedAuthor);
            }

            stmt.close();
            return authors;
        }
    }

    //GET Author by id
    public Optional<Author> findAuthorById(long id) throws SQLException {

        try(Connection conn = dataSource.getConnection()){

            String sql = "SELECT id, name FROM authors WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                long retrievedId = rs.getLong("id");
                String authorName = rs.getString("name");

                Author retrievedAuthor = new Author(retrievedId, authorName);

                stmt.close();
                return Optional.of(retrievedAuthor);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }
    }

    //CREATE Author
    public Author createAuthor(AuthorBindingDTO authorDTO) throws SQLException {

        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO authors(name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, authorDTO.getName());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            long id;
            if(generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                stmt.close();
                return new Author(id, authorDTO.getName());

            } else {
                throw new SQLException("Insertion failed. No ID obtained");
            }

        }
    }

    //GET Author by name
    public Optional<Author> findAuthorByName(String authorName) throws SQLException {
        try(Connection conn = dataSource.getConnection()){

            String sql = "SELECT id, name FROM authors WHERE name=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, authorName);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                long retrievedId = rs.getLong("id");

                Author retrievedAuthor = new Author(retrievedId, authorName);

                stmt.close();
                return Optional.of(retrievedAuthor);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }
    }
}
