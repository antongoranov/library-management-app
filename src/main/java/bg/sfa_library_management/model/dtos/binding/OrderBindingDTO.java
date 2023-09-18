package bg.sfa_library_management.model.dtos.binding;

import javax.validation.constraints.NotBlank;

public class OrderBindingDTO {

    @NotBlank(message = "Enter a valid book title!")
    private String bookTitle;

    public String getBookTitle() {
        return bookTitle;
    }

    public OrderBindingDTO setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }
}
