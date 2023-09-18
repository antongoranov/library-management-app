package bg.sfa_library_management.model.dtos.binding;


public class ClientLoginDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public ClientLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ClientLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
