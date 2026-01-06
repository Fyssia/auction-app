package io.github.fysia.auction_backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails {

    private final Long id;
    private final String email;
    private final String displayName;
    private final String passwordHash;
    private final String role;

    public AuthUser(Long id, String email, String displayName, String passwordHash, String role) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getRole() { return role; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security обычно ожидает префикс ROLE_
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() { return passwordHash; }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
