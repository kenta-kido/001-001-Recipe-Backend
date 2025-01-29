package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.entity.UserEntity;
import java.util.Optional;

/**
 * Repository interface for managing {@link UserEntity} persistence.
 * This interface provides database operations for handling user entities.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Checks if a user with the given email already exists.
     *
     * @param email The email address to check.
     * @return {@code true} if a user with the specified email exists, otherwise {@code false}.
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves a user entity by its email address.
     *
     * @param email The email address of the user.
     * @return An {@link Optional} containing the {@link UserEntity} if found, otherwise empty.
     */
    Optional<UserEntity> findByEmail(String email);
}
