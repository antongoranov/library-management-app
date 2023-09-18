package bg.sfa_library_management.model.dtos.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookBindingDTO {

    @NotBlank(message = "Title cannot be blank or null!")
    private String title;

    @NotBlank(message = "Author name cannot be blank or null!")
    private String authorName;

    @NotNull(message = "Date of publishing cannot be blank or null!")
//    @DateTimeFormat(pattern = "YYYY-mm-dd")
    private LocalDate dateOfPublishing;

    public String getTitle() {
        return title;
    }

    public BookBindingDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public BookBindingDTO setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }

    public BookBindingDTO setDateOfPublishing(LocalDate dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
        return this;
    }
}
