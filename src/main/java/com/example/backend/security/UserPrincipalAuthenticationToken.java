package com.example.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Custom authentication token that represents an authenticated user.
 * This class extends {@link AbstractAuthenticationToken} and is used to store user authentication details in the security context.
 */
public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    /**
     * Constructs an authentication token for a successfully authenticated user.
     *
     * @param principal The authenticated {@link UserPrincipal} containing user details.
     */
    public UserPrincipalAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities()); // Sets user roles/authorities
        this.principal = principal;
        setAuthenticated(true); // Marks this token as authenticated
    }

    /**
     * Since credentials (e.g., password) are not needed after authentication, this method returns {@code null}.
     *
     * @return {@code null} as credentials are not stored in this token.
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns the authenticated user details.
     *
     * @return The {@link UserPrincipal} representing the authenticated user.
     */
    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }
}
