package com.example.backend.service;

import com.example.backend.entity.IngredientsEntity;
import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.repository.IngredientsRepository;
import com.example.backend.repository.IngredientsSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for managing ingredient synonyms.
 * This service provides CRUD operations for {@link IngredientsSynonymEntity}.
 */
@Service
public class IngredientsSynonymService {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private IngredientsSynonymRepository ingredientsSynonymRepository;

    /**
     * Searches for synonyms that contain the given keyword (case-insensitive).
     *
     * @param synonym The keyword to search for.
     * @return A list of {@link IngredientsSynonymEntity} containing the given keyword.
     */
    public List<IngredientsSynonymEntity> searchBySynonym(String synonym) {
        return ingredientsSynonymRepository.findBySynonymContainingIgnoreCase(synonym);
    }

    /**
     * Retrieves all synonyms associated with a specific ingredient.
     *
     * @param ingredientId The ID of the ingredient.
     * @return A list of {@link IngredientsSynonymEntity} associated with the ingredient.
     */
    public List<IngredientsSynonymEntity> getSynonymsByIngredientId(Long ingredientId) {
        return ingredientsSynonymRepository.findByIngredientIngredientId(ingredientId);
    }

    /**
     * Adds a synonym to an existing ingredient.
     *
     * @param ingredientId The ID of the ingredient.
     * @param synonymName The synonym to add.
     * @return The saved {@link IngredientsSynonymEntity}.
     * @throws RuntimeException if the ingredient is not found.
     */
    public IngredientsSynonymEntity addSynonymToIngredient(Long ingredientId, String synonymName) {
        // Retrieve the ingredient by ID
        IngredientsEntity ingredient = ingredientsRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        // Create a new synonym entity
        IngredientsSynonymEntity synonym = new IngredientsSynonymEntity();
        synonym.setIngredient(ingredient);
        synonym.setSynonym(synonymName);

        // Save the synonym
        return ingredientsSynonymRepository.save(synonym);
    }

    /**
     * Creates a new ingredient synonym.
     *
     * @param synonym The synonym entity to create.
     * @return The saved {@link IngredientsSynonymEntity}.
     */
    public IngredientsSynonymEntity createSynonym(IngredientsSynonymEntity synonym) {
        return ingredientsSynonymRepository.save(synonym);
    }

    /**
     * Deletes a synonym by its ID.
     *
     * @param id The ID of the synonym to delete.
     */
    public void deleteSynonym(Long id) {
        ingredientsSynonymRepository.deleteById(id);
    }
}
