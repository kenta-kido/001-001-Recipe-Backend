package com.example.backend.repository;

import com.example.backend.entity.RecipeIngredientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link RecipeIngredientsEntity} persistence.
 * This interface provides database operations for handling the relationship between recipes and ingredients.
 */
@Repository
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredientsEntity, Long> {

    /**
     * Retrieves a list of ingredients associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe whose ingredients are to be retrieved.
     * @return A list of {@link RecipeIngredientsEntity} objects linked to the given recipe.
     */
    List<RecipeIngredientsEntity> findByRecipeRecipeId(Long recipeId);
}
