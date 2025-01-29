package com.example.backend.controller;

import com.example.backend.dto.PasswordChangeRequestDTO;
import com.example.backend.dto.UserRequestDTO;
import com.example.backend.dto.UserResponseDTO;
import com.example.backend.entity.UserEntity;
import com.example.backend.service.UserService;
import com.example.backend.security.UserPrincipal; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Update the authenticated user's email and additional information.
     *
     * @param currentUser     The currently authenticated user.
     * @param userRequestDTO  A DTO containing the fields to update (email, extraInfo).
     * @return A ResponseEntity containing the updated UserResponseDTO.
     */
    @PutMapping("/email")
    public ResponseEntity<?> updateUserInfo(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        // Retrieve the current user's details from the database
        UserEntity userEntity = userService.getUserById(currentUser.getUserId())
                                           .orElseThrow(() -> new RuntimeException("User not found"));

        // Update mutable fields
        if (userRequestDTO.getExtraInfo() != null) {
            userEntity.setExtraInfo(userRequestDTO.getExtraInfo());
        }
        if (userRequestDTO.getEmail() != null && !userRequestDTO.getEmail().isEmpty()) {
            userEntity.setEmail(userRequestDTO.getEmail());
        }

        // Save the updated user information
        UserEntity updatedUser = userService.saveUser(userEntity);

        // Convert the updated entity to a DTO and return it
        return ResponseEntity.ok(convertToResponseDTO(updatedUser));
    }

    /**
     * Convert a UserEntity to a UserResponseDTO.
     *
     * @param user The UserEntity to convert.
     * @return The corresponding UserResponseDTO.
     */
    private UserResponseDTO convertToResponseDTO(UserEntity user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setExtraInfo(user.getExtraInfo());
        return responseDTO;
    }

    /**
     * Change the authenticated user's password.
     *
     * @param passwordChangeRequest A DTO containing the current and new passwords.
     * @return A ResponseEntity indicating success or failure.
     */
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        // Retrieve the email from the current user's principal
        String email = ((UserPrincipal) authentication.getPrincipal()).getEmail();

        // Fetch the user from the database
        UserEntity user = userService.findByEmail(email)
                                     .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Verify the current password
        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Current password is incorrect");
        }

        // Update the password
        userService.updatePassword(user.getId(), passwordChangeRequest.getNewPassword());
        return ResponseEntity.ok().body("Password updated successfully");
    }

    /**
     * Debugging endpoint to retrieve the authenticated user's password stored in the database.
     *
     * @return A ResponseEntity containing the user's hashed password.
     */
    @GetMapping("/db-password")
    public ResponseEntity<?> getDatabasePassword() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        // Retrieve the email from the current user's principal
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String email = userPrincipal.getUsername();

        // Fetch the user from the database
        UserEntity userEntity = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok().body("Database Password: " + userEntity.getPassword());
    }
}
