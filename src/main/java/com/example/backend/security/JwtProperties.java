package com.example.backend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class for JWT properties.
 * This class loads JWT-related settings from application properties using the prefix "security.jwt".
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {

    /**
     * The secret key used for signing and verifying JWT tokens.
     * This should be kept secure and not exposed in public repositories.
     */
    private String secretKey;
}
