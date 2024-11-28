package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.LoginResponse;
import com.example.backend.security.JwtIssuer;
import com.example.backend.security.UserPrincipalAuthenticationToken;
import com.example.backend.service.AuthService;
import com.example.backend.security.UserPrincipal;
import com.example.backend.model.LoginRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService; 
    @CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
}
