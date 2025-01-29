package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for handling password change requests.
 * This class is used to transfer the current and new password
 * when a user requests a password update.
 */
@Getter
@Setter
public class PasswordChangeRequestDTO {

    /**
     * The current password of the user.
     * This field is required and cannot be blank.
     */
    @NotBlank(message = "Current password cannot be blank")
    private String currentPassword;

    /**
     * The new password the user wants to set.
     * This field is required and cannot be blank.
     */
    @NotBlank(message = "New password cannot be blank")
    private String newPassword;
}
