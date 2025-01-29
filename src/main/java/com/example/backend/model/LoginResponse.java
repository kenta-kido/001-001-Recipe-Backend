package com.example.backend.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Model representing a login response.
 * This class is used to transfer the access token to the client after a successful authentication.
 */
@Getter
@Builder
public class LoginResponse {

    /**
     * The JWT access token issued upon successful login.
     * This token is used for authenticating subsequent requests.
     */
    private final String accessToken;
}
