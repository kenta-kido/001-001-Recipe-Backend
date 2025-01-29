package com.example.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import lombok.RequiredArgsConstructor;

/**
 * Security configuration for the application.
 * This class defines authentication, authorization, CORS, CSRF, and session management settings.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    /**
     * Configures security settings, including authentication, authorization, and session management.
     *
     * @param http The {@link HttpSecurity} object for configuring security.
     * @return A {@link SecurityFilterChain} object representing the configured security settings.
     * @throws Exception If an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        // Add JWT authentication filter before the default authentication filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        http
            .cors()
            .and()
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection as JWT is used
            .sessionManagement(configurer -> configurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session to stateless for JWT authentication
            .securityMatcher("/**") // Apply security to all endpoints
            .authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/").permitAll() // Allow public access to the root endpoint
                .requestMatchers("/auth/login").permitAll() // Allow public access to login
                .requestMatchers("/admin", "/admin/**").hasRole("ADMIN") // Restrict admin endpoints to ADMIN role
                .requestMatchers(HttpMethod.GET, "/recipes", "/recipes/**").permitAll() // Allow GET requests to recipes
                .requestMatchers("/users/**").authenticated() // Require authentication for user-related endpoints
                .requestMatchers(HttpMethod.POST, "/recipes", "/recipes/**").hasRole("USER") // Restrict POST to USER role
                .requestMatchers(HttpMethod.PUT, "/recipes", "/recipes/**").hasRole("USER") // Restrict PUT to USER role
                .requestMatchers(HttpMethod.DELETE, "/recipes", "/recipes/**").hasRole("USER") // Restrict DELETE to USER role
                .requestMatchers("/secured/**").authenticated() // Require authentication for secured endpoints
                .anyRequest().permitAll() // Allow all other requests
            );

        return http.build();
    }

    /**
     * Defines the password encoder to be used for hashing passwords.
     *
     * @return A {@link BCryptPasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures and provides an authentication manager.
     *
     * @param http The {@link HttpSecurity} object for security configuration.
     * @return The configured {@link AuthenticationManager}.
     * @throws Exception If an error occurs while setting up authentication.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    /**
     * Configures CORS settings to allow requests from specified origins.
     *
     * @return A {@link CorsFilter} that applies CORS configuration.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://portfolio-kenta-926ed757a371.herokuapp.com");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
