package com.example.backend.service;

import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for managing recipe ingredients.
 * This service provides CRUD operations for {@link RecipeIngredientsEntity}.
 */
@Service
public class RecipeIngredientsService {

    @Autowired
    private RecipeIngredientsRepository recipeIngredientsRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientsRepository ingredientRepository;

    @Autowired
    private UnitRepository unitRepository;

    /**
     * Retrieves all ingredients associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe.
     * @return A list of {@link RecipeIngredientsEntity} associated with the given recipe.
     */
    public List<RecipeIngredientsEntity> getIngredientsByRecipeId(Long recipeId) {
        return recipeIngredientsRepository.findByRecipeRecipeId(recipeId);
    }

    /**
     * Adds an ingredient to a specific recipe.
     *
     * @param recipeId The ID of the recipe to which the ingredient will be added.
     * @param recipeIngredient The ingredient entity to be added.
     * @return The saved {@link RecipeIngredientsEntity}.
     * @throws RuntimeException if the recipe, ingredient, or unit is not found.
     */
    public RecipeIngredientsEntity addIngredientToRecipe(Long recipeId, RecipeIngredientsEntity recipeIngredient) {
        // Retrieve the recipe by ID
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Retrieve the ingredient by ID
        IngredientsEntity ingredient = ingredientRepository.findById(recipeIngredient.getIngredient().getIngredientId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        // Retrieve the unit by ID
        UnitEntity unit = unitRepository.findById(recipeIngredient.getUnit().getUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        // Set relationships
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setUnit(unit);

        return recipeIngredientsRepository.save(recipeIngredient);
    }

    /**
     * Updates an existing ingredient in a recipe.
     *
     * @param id The ID of the ingredient record to update.
     * @param updatedIngredient The updated ingredient details.
     * @return The updated {@link RecipeIngredientsEntity}.
     * @throws RuntimeException if the ingredient record is not found.
     */
    public RecipeIngredientsEntity updateIngredient(Long id, RecipeIngredientsEntity updatedIngredient) {
        RecipeIngredientsEntity existingIngredient = recipeIngredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RecipeIngredient not found"));

        existingIngredient.setQuantity(updatedIngredient.getQuantity());
        existingIngredient.setServings(updatedIngredient.getServings());
        existingIngredient.setUnit(updatedIngredient.getUnit());
        existingIngredient.setIngredient(updatedIngredient.getIngredient());

        return recipeIngredientsRepository.save(existingIngredient);
    }

    /**
     * Deletes an ingredient from a recipe by its ID.
     *
     * @param id The ID of the ingredient record to delete.
     * @throws RuntimeException if the ingredient record is not found.
     */
    public void deleteIngredient(Long id) {
        RecipeIngredientsEntity ingredient = recipeIngredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RecipeIngredient not found"));

        recipeIngredientsRepository.delete(ingredient);
    }
}
