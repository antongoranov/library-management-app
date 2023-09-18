package bg.sfa_library_management.model.dtos.view;


import java.util.Set;

public class ClientViewDTO {

    private long id;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private Set<String> roles;

    public long getId() {
        return id;
    }

    public ClientViewDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public ClientViewDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClientViewDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ClientViewDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ClientViewDTO setPassword(String password) {
        this.password = password;
        return this;
    }


    public Set<String> getRoles() {
        return roles;
    }

    public ClientViewDTO setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }
}
