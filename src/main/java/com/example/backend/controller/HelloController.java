package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * a Controller for testing basic functions
 */
@RestController
@RequiredArgsConstructor
public class HelloController {

    @Value("${test.default.password}")
    private String testDefaultPassword;

    /**
     * Public endpoint for testing the API deployment.
     * Returns a greeting message with a test environment variable.
     *
     * @return A greeting string containing the value of the environment variable.
     */
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello test, Heroku Auto Deploy, test env: " + testDefaultPassword;
    }

    /**
     * Secured endpoint accessible to authenticated users.
     * Returns a message containing the logged-in user's email and ID.
     *
     * @param principal The authenticated user's details provided by Spring Security.
     * @return A string confirming the user's authentication status and details.
     */
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        return "if you see this, then you are logged in as user " + principal.getEmail()
            + " / User ID: " + principal.getUserId();
    }

    /**
     * Secured endpoint accessible only to users with ADMIN privileges.
     * Returns a message confirming the user's ADMIN role and their user ID.
     *
     * @param principal The authenticated user's details provided by Spring Security.
     * @return A string confirming the user has ADMIN privileges and their details.
     */
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "if you see this, then you are ADMIN. User ID: " + principal.getUserId();
    }
}