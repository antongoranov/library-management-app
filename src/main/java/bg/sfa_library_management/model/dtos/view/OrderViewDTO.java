package bg.sfa_library_management.model.dtos.view;

import java.time.LocalDate;

public class OrderViewDTO {

    private long id;
    private String clientFullName;
    private BookViewDTO book;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public long getId() {
        return id;
    }

    public OrderViewDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public OrderViewDTO setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
        return this;
    }

    public BookViewDTO getBook() {
        return book;
    }

    public OrderViewDTO setBook(BookViewDTO book) {
        this.book = book;
        return this;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public OrderViewDTO setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public OrderViewDTO setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
