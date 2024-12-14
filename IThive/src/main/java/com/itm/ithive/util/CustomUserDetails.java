package com.itm.ithive.util;

import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Users;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collection;
import java.util.List;


@Data
public class CustomUserDetails implements UserDetails {
    private final String id;
    private final String password;
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());

        System.out.println("Current role is" + authority.getAuthority());

        return List.of(authority);
    }


    public CustomUserDetails(Users user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.role = user.getRole();
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

    private static final long serialVersionUID = 309623584880637894L;
    private static final int VERSION = 2; // remember to change version

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(VERSION);
    }

}
