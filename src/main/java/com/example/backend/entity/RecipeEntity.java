package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity representing a recipe.
 * This entity stores details about a recipe, including its title, user, photo, ingredients, and tags.
 */
@Entity
@Getter
@Setter
@Table(name = "Recipe")
public class RecipeEntity {

    /**
     * The unique identifier for the recipe.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    /**
     * The user who created the recipe.
     * Many recipes can belong to one user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key reference to UserEntity
    private UserEntity user;

    /**
     * The photo associated with this recipe.
     * Each recipe can have one thumbnail image.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "photoId", nullable = true)
    private PhotoEntity photo;

    /**
     * The title of the recipe.
     * Cannot be null and has a maximum length of 255 characters.
     */
    @Column(nullable = false, length = 255)
    private String title;

    /**
     * A list of descriptions (steps) associated with this recipe.
     * Cascade operations ensure that all descriptions are removed when the recipe is deleted.
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DescriptionEntity> descriptions;

    /**
     * A list of ingredients used in this recipe.
     * Cascade operations ensure that all ingredient relations are removed when the recipe is deleted.
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RecipeIngredientsEntity> recipeIngredients;

    /**
     * A list of tags associated with this recipe.
     * Cascade operations ensure that all tag relations are removed when the recipe is deleted.
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TagRecipeEntity> tagRecipes;

    /**
     * The timestamp indicating when the recipe was created or last updated.
     * Automatically set before saving or updating the entity.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * Sets the timestamp before inserting a new record.
     */
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Updates the timestamp before updating an existing record.
     */
    @PreUpdate
    protected void onUpdate() {
        this.timestamp = LocalDateTime.now();
    }
}
