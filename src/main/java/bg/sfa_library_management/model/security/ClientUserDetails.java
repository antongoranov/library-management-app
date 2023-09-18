package bg.sfa_library_management.model.security;

import bg.sfa_library_management.model.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

public class ClientUserDetails implements UserDetails {

    private final long id;
    private final String fullName;
    private final String username;
    private final String password;
    private final String email;
    private final Collection<GrantedAuthority> authorities;

    public ClientUserDetails(long id,
                             String fullName,
                             String username,
                             String password,
                             String email,
                             Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return fullName;
    }

    public long getId() {
        return id;
    }
}
