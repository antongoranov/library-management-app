package bg.sfa_library_management.model.entity;

import bg.sfa_library_management.model.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public Role() {}
    public Role(long id, RoleEnum role) {
        this.id = id;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Role setId(long id) {
        this.id = id;
        return this;
    }

    public RoleEnum getRole() {
        return role;
    }

    public Role setRole(RoleEnum name) {
        this.role = name;
        return this;
    }

    @Override
    public String toString() {
        return role.toString();
    }
}
