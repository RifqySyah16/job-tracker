package com.rifqy.potofolio.job_tracker.authentication.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;

public class UserPrincipal implements UserDetails {
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String email;

    @Getter
    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(Long id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = Collections.emptyList();
    }

    public static UserDetails build(ApplicationUser applicationUser) {
        return new UserPrincipal(applicationUser.getId(), applicationUser.getName(), applicationUser.getEmail(),
                applicationUser.getUsername(), applicationUser.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
