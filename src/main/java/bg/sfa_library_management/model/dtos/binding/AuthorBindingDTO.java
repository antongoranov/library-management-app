package bg.sfa_library_management.model.dtos.binding;

import javax.validation.constraints.NotBlank;

public class AuthorBindingDTO {

    @NotBlank(message = "Username cannot be blank!")
    private String name;

    public AuthorBindingDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
