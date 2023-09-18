package bg.sfa_library_management.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false)
    private Author author;

    @Column(name = "date_of_publishing", nullable = false)
    private LocalDate dateOfPublishing;

    public Book() {}
    public Book(long id, String title, Author author, LocalDate dateOfPublishing) {
        this.id = id;
        this.title = title;
        this.author = author;

        this.dateOfPublishing = dateOfPublishing;
    }

    public long getId() {
        return id;
    }

    public Book setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String name) {
        this.title = name;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public Book setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }

    public Book setDateOfPublishing(LocalDate dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
        return this;
    }
}
