package bg.sfa_library_management.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Client client;

    @OneToOne
    private Book book;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    //All books should be returned a month after they were borrowed from the library
    @Column(name = "due_date")
    private LocalDate dueDate;

    public Order() {
    }

    public Order(long id, Client client, Book book, LocalDate issueDate, LocalDate dueDate) {
        this.id = id;
        this.client = client;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public Order setId(long id) {
        this.id = id;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Order setClient(Client client) {
        this.client = client;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Order setBook(Book book) {
        this.book = book;
        return this;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public Order setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Order setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
