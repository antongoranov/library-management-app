package bg.sfa_library_management.dao;

import bg.sfa_library_management.model.dtos.binding.BookBindingDTO;
import bg.sfa_library_management.model.entity.Author;
import bg.sfa_library_management.model.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final DataSource dataSource;
    private final AuthorDAO authorDAO;

    @Autowired
    public BookDAO(DataSource dataSource, AuthorDAO authorDAO) {
        this.dataSource = dataSource;
        this.authorDAO = authorDAO;
    }

    public List<Book> findAllBooks() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<Book> fetchedBooks = new ArrayList<>();

            String sql = "SELECT id, title, date_of_publishing, author_id FROM books";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");

                //check the conversion of the dates
                Date retrievedDate = rs.getDate("date_of_publishing");
                LocalDate publishingDate = retrievedDate.toLocalDate();

                long authorId = rs.getLong("author_id");

                Optional<Author> authorOpt = this.authorDAO.findAuthorById(authorId);

                if (authorOpt.isEmpty()) {
                    throw new SQLException("Invalid author id!");
                }

                Book book = new Book(id, title, authorOpt.get(), publishingDate);

                fetchedBooks.add(book);
            }

            stmt.close();
            return fetchedBooks;
        }
    }

    public Optional<Book> findBookById(long id) throws SQLException {
        try(Connection conn = dataSource.getConnection()){

            String sql = "SELECT id, title, date_of_publishing, author_id FROM books WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                long retrievedId = rs.getLong("id");
                String title = rs.getString("title");

                Date retrievedDate = rs.getDate("date_of_publishing");
                LocalDate publishingDate = retrievedDate.toLocalDate();
                long authorId = rs.getLong("author_id");

                //all existing books have valid authors
                Author bookAuthor = authorDAO.findAuthorById(authorId).get();

                Book retrievedBook = new Book(retrievedId, title, bookAuthor, publishingDate);

                stmt.close();
                return Optional.of(retrievedBook);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }

    }

    public Optional<Book> findBookByTitle(String title) throws SQLException {
        try(Connection conn = dataSource.getConnection()){

            String sql = "SELECT id, title, date_of_publishing, author_id FROM books WHERE title=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                long retrievedId = rs.getLong("id");

                Date retrievedDate = rs.getDate("date_of_publishing");
                LocalDate publishingDate = retrievedDate.toLocalDate();
                long authorId = rs.getLong("author_id");

                //all existing books have valid authors
                Author bookAuthor = authorDAO.findAuthorById(authorId).get();

                Book retrievedBook = new Book(retrievedId, title, bookAuthor, publishingDate);

                stmt.close();
                return Optional.of(retrievedBook);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }

    }

    public Book createBook(BookBindingDTO bookBindingDTO) throws SQLException {

        try (Connection conn = dataSource.getConnection()){

            String sql = "INSERT INTO books(title, date_of_publishing, author_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, bookBindingDTO.getTitle());

            //check the formatting in the DTO
            stmt.setString(2, bookBindingDTO.getDateOfPublishing().toString());

            Optional<Author> optAuthorByName = this.authorDAO.findAuthorByName(bookBindingDTO.getAuthorName());

            if (optAuthorByName.isEmpty()) {
                throw new SQLException("Invalid author name!");
            }

            stmt.setLong(3, optAuthorByName.get().getId());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            long id;
            if(generatedKeys.next()){
                id = generatedKeys.getLong(1);
                stmt.close();

                return new Book(
                        id,
                        bookBindingDTO.getTitle(),
                        optAuthorByName.get(),
                        bookBindingDTO.getDateOfPublishing());
            } else {
                throw new SQLException("Insertion failed. No ID obtained");
            }
        }
    }
}
