package bg.sfa_library_management.model.dtos.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClientRegistrationDTO {

    @NotBlank(message = "Name cannot be null or empty")
    @Size(min = 2,max = 30, message = "Name length should be between 2 and 50 symbols")
    private String fullName;

    @NotBlank(message = "Username cannot be null or empty")
    @Size(min = 3,max = 25, message = "Username length should be between 3 and 25 symbols")
    private String username;

    @NotBlank(message = "Password cannot be null or empty")
    @Size(min = 5,max = 30, message = "Password length should be between 5 and 30 symbols")
    private String password;

    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email must be in valid format")
    private String email;

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
