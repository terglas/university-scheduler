package com.example.universityscheduler.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    @Transient
    private final UserAccount userAccount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO realize Role
        return Collections.singletonList(
                new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return this.userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userAccount.getEmail();
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
}
