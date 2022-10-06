package pl.maciejowsky.bankapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.maciejowsky.bankapp.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements UserDetails {
    private User user;

    public UserPrincipal(User user) {

        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        this.user.getAuthoritiesList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorityList.add(authority);
        });
        this.user.getRolesList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r.toUpperCase());
            authorityList.add(authority);
        });
        return authorityList;

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
       return !(user.isLocked());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPrincipal)) return false;

        UserPrincipal that = (UserPrincipal) o;

        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }
}
