package com.example.backend.security;

import java.io.IOException;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JWT authentication filter that processes authentication for each request.
 * This filter extracts the JWT token from the request, decodes it, converts it into a user principal,
 * and sets the authentication context in Spring Security.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    /**
     * Processes the JWT authentication by extracting the token, decoding it,
     * converting it to a user principal, and setting the authentication context.
     *
     * @param request The HTTP request containing the JWT.
     * @param response The HTTP response.
     * @param filterChain The filter chain to continue processing.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode) // Decodes the JWT
                .map(jwtToPrincipalConverter::convert) // Converts the decoded JWT into a user principal
                .map(UserPrincipalAuthenticationToken::new) // Wraps the principal in an authentication token
                .ifPresent(authentication -> 
                    SecurityContextHolder.getContext().setAuthentication(authentication) // Sets authentication in the security context
                );

        filterChain.doFilter(request, response); // Ensures the filter chain continues
    }

    /**
     * Extracts the JWT token from the Authorization header of the request.
     *
     * @param request The HTTP request.
     * @return An {@link Optional} containing the JWT token if present, otherwise empty.
     */
    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7)); // Removes "Bearer " prefix
        }
        return Optional.empty();
    }
}
