package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing the relationship between tags and recipes.
 * This entity establishes a many-to-many relationship where a recipe can have multiple tags,
 * and a tag can be associated with multiple recipes.
 */
@Entity
@Getter
@Setter
@Table(name = "TagRecipe")
public class TagRecipeEntity {

    /**
     * The unique identifier for the tag-recipe relationship.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagRecipeId;

    /**
     * The tag associated with this recipe.
     * Many tag-recipe relationships can be linked to one tag.
     */
    @ManyToOne
    @JoinColumn(name = "tagId", nullable = false) // Foreign key reference to TagEntity
    private TagEntity tag;

    /**
     * The recipe associated with this tag.
     * Many tag-recipe relationships can be linked to one recipe.
     */
    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false) // Foreign key reference to RecipeEntity
    private RecipeEntity recipe;
}
