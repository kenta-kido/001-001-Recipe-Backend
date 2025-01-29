package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity representing an ingredient.
 * This entity stores ingredient information and its relationships with synonyms and recipes.
 */
@Entity
@Getter
@Setter
@Table(name = "Ingredients")
public class IngredientsEntity {

    /**
     * The unique identifier for the ingredient.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    /**
     * The name of the ingredient.
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * A list of synonyms associated with this ingredient.
     * One ingredient can have multiple synonyms.
     * Cascade operations ensure that synonyms are removed when the ingredient is deleted.
     */
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<IngredientsSynonymEntity> synonyms = new ArrayList<>();

    /**
     * A list of recipes that include this ingredient.
     * One ingredient can be part of multiple recipes.
     * Cascade operations ensure that references are removed when the ingredient is deleted.
     */
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RecipeIngredientsEntity> recipeIngredients;
}
