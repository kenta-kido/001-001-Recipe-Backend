package com.example.backend.security;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * A component responsible for converting a decoded JWT into a {@link UserPrincipal}.
 * This class extracts user details and authorities from the JWT claims.
 */
@Component
public class JwtToPrincipalConverter {

    /**
     * Converts a decoded JWT into a {@link UserPrincipal}.
     *
     * @param jwt The decoded JWT.
     * @return A {@link UserPrincipal} containing user information.
     */
    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                            .userId(Long.valueOf(jwt.getSubject())) // Extracts user ID from the "sub" claim
                            .email(jwt.getClaim("e").asString()) // Extracts email from the "e" claim
                            .authorities(extractAuthoritiesFromClaim(jwt)) // Extracts roles/authorities
                            .build();
    }

    /**
     * Extracts authorities (roles) from the "a" claim in the JWT.
     *
     * @param jwt The decoded JWT.
     * @return A list of {@link SimpleGrantedAuthority} representing user roles.
     */
    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("a");
        if (claim.isNull() || claim.isMissing()) return List.of(); // Returns an empty list if no authorities are found
        return claim.asList(SimpleGrantedAuthority.class); // Converts claim values into authority objects
    }
}
