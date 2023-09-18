package bg.sfa_library_management.model.dtos.view;


import java.time.LocalDate;

public class BookViewDTO {

    private long id;
    private String title;
    private String authorName;
    private LocalDate dateOfPublishing;

    public long getId() {
        return id;
    }

    public BookViewDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookViewDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public BookViewDTO setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }

    public BookViewDTO setDateOfPublishing(LocalDate dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
        return this;
    }
}
