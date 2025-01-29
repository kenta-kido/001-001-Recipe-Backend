package com.example.backend.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Model representing a login request.
 * This class is used to transfer login credentials from the client to the server.
 */
@Getter
@Builder
public class LoginRequest {

    /**
     * The email address of the user attempting to log in.
     */
    private String email;

    /**
     * The password of the user attempting to log in.
     */
    private String password;
}
