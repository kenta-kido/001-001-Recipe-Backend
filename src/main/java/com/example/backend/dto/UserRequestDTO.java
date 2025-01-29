package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for user registration and update requests.
 * This class is used to transfer user-related data when creating or updating a user.
 */
@Getter
@Setter
public class UserRequestDTO {

    /**
     * The email address of the user.
     * Must be a valid email format and cannot be blank.
     */
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * The password for the user.
     * Cannot be blank and should meet security requirements.
     * Uncomment @Size annotation to enforce minimum password length.
     */
    @NotBlank(message = "Password is required")
    // @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    /**
     * The role assigned to the user (e.g., ADMIN, USER).
     * This field is required.
     */
    @NotBlank(message = "Role is required")
    private String role;

    /**
     * Additional user information (optional).
     * This field can be null or empty.
     */
    private String extraInfo;
}
