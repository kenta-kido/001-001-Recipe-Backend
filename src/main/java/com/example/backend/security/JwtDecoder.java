package com.example.backend.security;

import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;

/**
 * A component responsible for decoding and verifying JWT tokens.
 * This class ensures the token's validity using the secret key defined in {@link JwtProperties}.
 */
@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtProperties properties;

    /**
     * Decodes and verifies a JWT token.
     *
     * @param token The JWT token to decode and verify.
     * @return A {@link DecodedJWT} object containing the token's claims and payload.
     * @throws com.auth0.jwt.exceptions.JWTVerificationException If the token is invalid or tampered with.
     */
    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey())) // Uses HMAC256 for token verification
                .build()
                .verify(token); // Verifies the token's integrity
    }
}
