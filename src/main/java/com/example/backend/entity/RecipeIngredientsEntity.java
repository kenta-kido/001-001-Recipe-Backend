package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing the relationship between recipes and ingredients.
 * This entity stores the quantity and unit of each ingredient used in a recipe.
 */
@Entity
@Getter
@Setter
@Table(name = "RecipeIngredients")
public class RecipeIngredientsEntity {

    /**
     * The unique identifier for the recipe-ingredient relationship.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeIngredientsId;

    /**
     * The recipe associated with this ingredient.
     * Many ingredients can belong to one recipe.
     */
    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false) // Foreign key reference to RecipeEntity
    private RecipeEntity recipe;

    /**
     * The ingredient used in this recipe.
     * Many recipes can use the same ingredient.
     */
    @ManyToOne
    @JoinColumn(name = "ingredientId", nullable = false) // Foreign key reference to IngredientsEntity
    private IngredientsEntity ingredient;

    /**
     * The number of servings this ingredient quantity applies to.
     * This value is required.
     */
    @Column(nullable = false)
    private Integer servings;

    /**
     * The quantity of the ingredient used in the recipe.
     * This value is required.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * The unit of measurement for the ingredient quantity (e.g., g, kg, mL).
     * Many recipe-ingredient relationships can use the same unit.
     */
    @ManyToOne
    @JoinColumn(name = "unitId", nullable = false) // Foreign key reference to UnitEntity
    private UnitEntity unit;
}
