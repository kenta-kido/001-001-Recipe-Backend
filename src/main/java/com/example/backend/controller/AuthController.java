package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.LoginResponse;
import com.example.backend.service.AuthService;
import com.example.backend.model.LoginRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user login requests.
     * Accepts a login request with email and password, validates the input,
     * and returns a LoginResponse containing authentication details.
     *
     * @param request The login request containing the user's email and password.
     * @return A LoginResponse with authentication details or an error message.
     */
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
}
