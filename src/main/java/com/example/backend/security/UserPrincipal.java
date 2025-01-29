package com.example.backend.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

/**
 * Custom implementation of {@link UserDetails} for authentication and authorization.
 * This class represents the authenticated user and is used by Spring Security.
 */
@Getter
@Builder
public class UserPrincipal implements UserDetails {

    /**
     * The unique identifier of the user.
     */
    private final Long userId;

    /**
     * The email address of the user, used as the username in authentication.
     */
    private final String email;

    /**
     * The hashed password of the user. This field is ignored when serializing to JSON.
     */
    @JsonIgnore
    private final String password;

    /**
     * The authorities (roles/permissions) granted to the user.
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Returns the authorities granted to the user.
     *
     * @return A collection of {@link GrantedAuthority} objects representing the user's roles/permissions.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the user's password.
     *
     * @return The hashed password of the user.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username (email) used for authentication.
     *
     * @return The email address of the user.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     * Returning {@code true} means the account is valid and not expired.
     *
     * @return {@code true}, indicating that the account is not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Always return true unless account expiration is implemented
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * Returning {@code true} means the user is not locked.
     *
     * @return {@code true}, indicating that the account is not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Always return true unless account locking is implemented
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * Returning {@code true} means the credentials are valid.
     *
     * @return {@code true}, indicating that the credentials are not expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Always return true unless credential expiration is implemented
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * Returning {@code true} means the user is active.
     *
     * @return {@code true}, indicating that the user is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true; // Always return true unless user disabling is implemented
    }
}
