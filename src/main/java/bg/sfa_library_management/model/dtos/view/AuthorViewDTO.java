package bg.sfa_library_management.model.dtos.view;

public class AuthorViewDTO {

    private long id;
    private String name;

    public AuthorViewDTO() {
    }

    public long getId() {
        return id;
    }

    public AuthorViewDTO setId(long id) {
        this.id = id;
        return this;
    }

    public AuthorViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
