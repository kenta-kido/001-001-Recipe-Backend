package com.example.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.backend.model.LoginResponse;
import com.example.backend.security.JwtIssuer;
import com.example.backend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;

/**
 * Service responsible for handling authentication and JWT issuance.
 * This class manages user login attempts and generates JWT tokens upon successful authentication.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    /**
     * Attempts to authenticate a user using email and password.
     * If authentication is successful, a JWT token is generated and returned.
     *
     * @param email The user's email address used for authentication.
     * @param password The user's password.
     * @return A {@link LoginResponse} containing the JWT access token.
     * @throws org.springframework.security.authentication.BadCredentialsException If authentication fails.
     */
    public LoginResponse attemptLogin(String email, String password) {

        // Authenticate user with email and password
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        // Store authentication details in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Retrieve authenticated user details
        var principal = (UserPrincipal) authentication.getPrincipal();

        // Extract user roles from granted authorities
        var roles = principal.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList();

        // Generate JWT token
        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);

        // Return the token in the response
        return LoginResponse.builder()
                            .accessToken(token)
                            .build();
    }
}
