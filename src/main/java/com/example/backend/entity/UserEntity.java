package com.example.backend.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a user in the system.
 * This entity stores user authentication details and associated recipes.
 */
@Entity
@Getter
@Setter
public class UserEntity {

    /**
     * The unique identifier for the user.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The email address of the user.
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The hashed password of the user.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The role assigned to the user (e.g., ADMIN, USER).
     * Determines access privileges.
     */
    private String role;

    /**
     * Additional user information.
     * This field is optional.
     */
    private String extraInfo;

    /**
     * A list of recipes created by the user.
     * Cascade operations ensure that all recipes are removed when the user is deleted.
     */
    @JsonIgnore 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeEntity> recipes;
}
