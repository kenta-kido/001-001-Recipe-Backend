package com.example.backend.controller;

import com.example.backend.entity.RecipeIngredientsEntity;
import com.example.backend.service.RecipeIngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/recipes/{recipeId}/ingredients")
public class RecipeIngredientsController {

    @Autowired
    private RecipeIngredientsService recipeIngredientsService;

    /**
     * Fetch all ingredients associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe whose ingredients are to be retrieved.
     * @return A list of RecipeIngredientsEntity objects associated with the recipe.
     */
    @GetMapping
    public List<RecipeIngredientsEntity> getIngredientsByRecipeId(@PathVariable Long recipeId) {
        return recipeIngredientsService.getIngredientsByRecipeId(recipeId);
    }

    /**
     * Add a new ingredient to a specific recipe.
     *
     * @param recipeId         The ID of the recipe to which the ingredient will be added.
     * @param recipeIngredient The RecipeIngredientsEntity object representing the new ingredient.
     * @return A ResponseEntity containing the created RecipeIngredientsEntity object.
     */
    @PostMapping
    public ResponseEntity<RecipeIngredientsEntity> addIngredientToRecipe(
            @PathVariable Long recipeId,
            @RequestBody RecipeIngredientsEntity recipeIngredient
    ) {
        RecipeIngredientsEntity createdIngredient = recipeIngredientsService.addIngredientToRecipe(recipeId, recipeIngredient);
        return ResponseEntity.ok(createdIngredient);
    }

    /**
     * Update an existing ingredient associated with a recipe.
     *
     * @param id              The ID of the ingredient to update.
     * @param updatedIngredient The updated RecipeIngredientsEntity object.
     * @return A ResponseEntity containing the updated RecipeIngredientsEntity object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredientsEntity> updateIngredient(
            @PathVariable Long id,
            @RequestBody RecipeIngredientsEntity updatedIngredient
    ) {
        RecipeIngredientsEntity updated = recipeIngredientsService.updateIngredient(id, updatedIngredient);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete an ingredient by its ID.
     *
     * @param id The ID of the ingredient to delete.
     * @return A ResponseEntity with no content upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        recipeIngredientsService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}