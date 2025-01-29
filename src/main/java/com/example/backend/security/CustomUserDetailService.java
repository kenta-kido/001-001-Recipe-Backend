package com.example.backend.security;

import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.example.backend.service.UserService;

/**
 * Custom implementation of {@link UserDetailsService} for authentication.
 * This service retrieves user details from the database and converts them into Spring Security's {@link UserDetails}.
 */
@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    /**
     * Constructor with {@link UserService} dependency.
     * Uses {@code @Lazy} to avoid circular dependency issues.
     *
     * @param userService The user service to fetch user details.
     */
    public CustomUserDetailService(@Lazy UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads a user by their username (email) and converts it to a {@link UserDetails} object.
     *
     * @param username The email address of the user.
     * @return A {@link UserDetails} object containing user information for authentication.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByEmail(username).orElseThrow(() -> 
            new UsernameNotFoundException("User not found with email: " + username)
        );

        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .password(user.getPassword())
                .build();
    }
}
