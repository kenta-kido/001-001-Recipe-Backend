package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for returning user information in API responses.
 * This class is used to transfer user-related data when responding to client requests.
 */
@Getter
@Setter
public class UserResponseDTO {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The role assigned to the user (e.g., ADMIN, USER).
     */
    private String role;

    /**
     * Additional user information (optional).
     */
    private String extraInfo;
}
