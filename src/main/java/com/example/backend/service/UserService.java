package com.example.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import java.util.List;

/**
 * Service for managing users.
 * This service provides CRUD operations for {@link UserEntity} and handles user authentication and password encryption.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${dev.default.password}")
    private String devDefaultPassword;

    @Value("${test.default.password}")
    private String testDefaultPassword;

    /**
     * Initializes default admin and user accounts if they do not exist.
     * This method is automatically executed after dependency injection.
     */
    @PostConstruct
    public void initAdminUser() {
        createDefaultUser("admin@kenta.com", devDefaultPassword, "ROLE_ADMIN", "Admin for Developer");
        createDefaultUser("user@kenta.com", devDefaultPassword, "ROLE_USER", "User for Developer");
        createDefaultUser("admintest@kenta.com", testDefaultPassword, "ROLE_ADMIN", "Admin for Test");
        createDefaultUser("usertest@kenta.com", testDefaultPassword, "ROLE_USER", "User for Test");
    }

    /**
     * Creates a default user if they do not exist in the database.
     *
     * @param email The email of the user.
     * @param password The default password.
     * @param role The user role.
     * @param extraInfo Additional information about the user.
     */
    private void createDefaultUser(String email, String password, String role, String extraInfo) {
        if (userRepository.findByEmail(email).isEmpty()) {
            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password)); 
            user.setRole(role);
            user.setExtraInfo(extraInfo);
            userRepository.save(user);
        }
    }

    /**
     * Finds a user by their email.
     *
     * @param email The email of the user.
     * @return An {@link Optional} containing the {@link UserEntity} if found.
     */
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves or updates a user.
     * If the password has changed, it is hashed before saving.
     *
     * @param user The user entity to save.
     * @return The saved {@link UserEntity}.
     * @throws RuntimeException if the user does not exist.
     */
    public UserEntity saveUser(UserEntity user) {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Hash the password only if it has been changed
        if (!user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword()); 
        }

        return userRepository.save(user);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all {@link UserEntity} objects.
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user.
     * @return An {@link Optional} containing the {@link UserEntity} if found.
     */
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Updates user information.
     * If the password has changed, it is hashed before saving.
     *
     * @param id The ID of the user to update.
     * @param updatedUser The updated user details.
     * @return The updated {@link UserEntity}.
     * @throws RuntimeException if the user is not found.
     */
    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());

                    // Hash the password only if it has changed
                    if (!updatedUser.getPassword().equals(user.getPassword())) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    user.setRole(updatedUser.getRole());
                    user.setExtraInfo(updatedUser.getExtraInfo());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    /**
     * Updates a user's password.
     *
     * @param userId The ID of the user.
     * @param newPassword The new password.
     */
    public void updatePassword(Long userId, String newPassword) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));

            // Remove any existing initial password info
            if (user.getExtraInfo() != null && user.getExtraInfo().contains("[Init Pass:")) {
                user.setExtraInfo(user.getExtraInfo().replaceAll("\\[Init Pass: .*?\\]", "").trim());
            }

            // Append password update information
            user.setExtraInfo("Password updated by user");

            userRepository.save(user);
        });
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
