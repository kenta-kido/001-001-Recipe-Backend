package com.example.backend.security;

import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * A component responsible for issuing JWT tokens.
 * This class generates and signs JWTs using a secret key for authentication and authorization.
 */
@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties properties;

    /**
     * Issues a new JWT token for an authenticated user.
     *
     * @param userId The unique identifier of the user.
     * @param email The email of the authenticated user.
     * @param roles A list of roles assigned to the user.
     * @return A signed JWT token containing the user's details and expiration time.
     */
    public String issue(long userId, String email, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId)) // Sets the user ID as the subject
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS))) // Token expires in 1 day
                .withClaim("e", email) // Adds the email claim
                .withClaim("a", roles) // Adds the roles claim
                .sign(Algorithm.HMAC256(properties.getSecretKey())); // Signs the token using the secret key
    }
}
