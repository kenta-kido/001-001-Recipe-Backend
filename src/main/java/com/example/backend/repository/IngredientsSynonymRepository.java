package com.example.backend.repository;

import com.example.backend.entity.IngredientsSynonymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link IngredientsSynonymEntity} persistence.
 * This interface provides database operations for ingredient synonyms.
 */
@Repository
public interface IngredientsSynonymRepository extends JpaRepository<IngredientsSynonymEntity, Long> {

    /**
     * Retrieves a list of ingredient synonyms that contain the given keyword (case insensitive).
     *
     * @param synonym The keyword to search for in synonyms.
     * @return A list of {@link IngredientsSynonymEntity} objects matching the keyword.
     */
    List<IngredientsSynonymEntity> findBySynonymContainingIgnoreCase(String synonym);

    /**
     * Retrieves a list of synonyms associated with a specific ingredient.
     *
     * @param ingredientId The ID of the ingredient whose synonyms are to be retrieved.
     * @return A list of {@link IngredientsSynonymEntity} objects linked to the given ingredient.
     */
    List<IngredientsSynonymEntity> findByIngredientIngredientId(Long ingredientId);
}
