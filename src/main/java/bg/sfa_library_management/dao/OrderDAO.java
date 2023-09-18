package bg.sfa_library_management.dao;

import bg.sfa_library_management.model.dtos.binding.OrderBindingDTO;
import bg.sfa_library_management.model.entity.Author;
import bg.sfa_library_management.model.entity.Book;
import bg.sfa_library_management.model.entity.Client;
import bg.sfa_library_management.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {

    private final DataSource dataSource;
    private final BookDAO bookDao;
    private final ClientDAO clientDao;

    @Autowired
    public OrderDAO(DataSource dataSource,
                    BookDAO bookDao,
                    ClientDAO clientDao) {

        this.dataSource = dataSource;
        this.bookDao = bookDao;
        this.clientDao = clientDao;
    }


    public Optional<Order> findOrderById(long id) throws SQLException {

        try(Connection conn = dataSource.getConnection()){

            String sql = "SELECT id, issue_date, due_date, book_id, client_id FROM orders WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Order order = orderCreation(rs);
                stmt.close();
                return Optional.of(order);
            } else {
                stmt.close();
                return Optional.empty();
            }
        }
    }

    public List<Order> findAll() throws SQLException {

        try (Connection conn = dataSource.getConnection()) {
            List<Order> fetchedOrders = new ArrayList<>();

            String sql = "SELECT id, issue_date, due_date, book_id, client_id FROM orders";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = orderCreation(rs);
                fetchedOrders.add(order);
            }

            stmt.close();
            return fetchedOrders;
        }
    }

    public Order saveOrder(OrderBindingDTO orderBindingDTO, long clientId) throws SQLException {

        try (Connection conn = dataSource.getConnection()){

            String sql = "INSERT INTO orders(issue_date, due_date, book_id, client_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // will always have a valid client id, cause the client is logged
            Optional<Client> optClient = this.clientDao.findClientById(clientId);

            Optional<Book> optBookByName = this.bookDao.findBookByTitle(orderBindingDTO.getBookTitle());

            if (optBookByName.isEmpty()) {
                throw new SQLException("Invalid book title!");
            }
            LocalDate issueDate = LocalDate.now();
            LocalDate dueDate = LocalDate.now().plusMonths(1);

            stmt.setString(1,issueDate.toString());
            stmt.setString(2, dueDate.toString());
            stmt.setLong(3, optBookByName.get().getId());
            stmt.setLong(4, optClient.get().getId());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            long id;
            if(generatedKeys.next()){
                id = generatedKeys.getLong(1);
                stmt.close();

                return new Order(
                        id,
                        optClient.get(),
                        optBookByName.get(),
                        issueDate,
                        dueDate);
            } else {
                throw new SQLException("Insertion failed. No ID obtained");
            }
        }

    }

    public List<Order> findAllByClientId(long clientId) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {
            List<Order> fetchedOrders = new ArrayList<>();

            String sql = "SELECT id, issue_date, due_date, book_id, client_id FROM orders WHERE client_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            Optional<Client> optClient = this.clientDao.findClientById(clientId);
            if(optClient.isEmpty()) {
                throw new SQLException("Invalid Client!");
            }

            stmt.setLong(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = orderCreation(rs);
                fetchedOrders.add(order);
            }
            stmt.close();
            return fetchedOrders;
        }
    }

    public void deleteById(long id) throws SQLException {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM orders WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    private Order orderCreation(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");

        Date dateOfIssuing = rs.getDate("issue_date");
        LocalDate issueDate = dateOfIssuing.toLocalDate();

        Date returnDueDate = rs.getDate("due_date");
        LocalDate dueDate = returnDueDate.toLocalDate();

        long bookId = rs.getLong("book_id");
        long clientId = rs.getLong("client_id");

        Optional<Client> optClient = this.clientDao.findClientById(clientId);
        Optional<Book> optBook = this.bookDao.findBookById(bookId);

        return new Order(id, optClient.get(), optBook.get(), issueDate, dueDate);
    }
}
