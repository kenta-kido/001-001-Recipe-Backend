package com.example.backend.service;

import com.example.backend.entity.IngredientsEntity;
import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.repository.IngredientsRepository;
import com.example.backend.repository.IngredientsSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for managing ingredients and their synonyms.
 * This service provides CRUD operations for {@link IngredientsEntity} and handles ingredient synonyms.
 */
@Service
public class IngredientsService {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private IngredientsSynonymRepository ingredientsSynonymRepository;

    /**
     * Retrieves a list of all ingredients.
     *
     * @return A list of all {@link IngredientsEntity}.
     */
    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsRepository.findAll();
    }

    /**
     * Retrieves an ingredient by its ID.
     *
     * @param id The ID of the ingredient.
     * @return The {@link IngredientsEntity} if found.
     * @throws RuntimeException if the ingredient is not found.
     */
    public IngredientsEntity getIngredientById(Long id) {
        return ingredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    /**
     * Creates a new ingredient and automatically registers its default synonym.
     *
     * @param ingredient The ingredient entity to create.
     * @return The saved {@link IngredientsEntity}.
     * @throws RuntimeException if the ingredient already exists.
     */
    public IngredientsEntity createIngredient(IngredientsEntity ingredient) {
        // Check for duplicate ingredient names
        if (ingredientsRepository.existsByName(ingredient.getName())) {
            throw new RuntimeException("Ingredient already exists");
        }

        // Save the ingredient
        IngredientsEntity savedIngredient = ingredientsRepository.save(ingredient);

        // Register the default synonym (same as the ingredient name)
        IngredientsSynonymEntity synonym = new IngredientsSynonymEntity();
        synonym.setIngredient(savedIngredient);
        synonym.setSynonym(ingredient.getName()); // Use the ingredient name as the default synonym

        ingredientsSynonymRepository.save(synonym);
        return savedIngredient;
    }

    /**
     * Deletes an ingredient by its ID.
     *
     * @param id The ID of the ingredient to delete.
     */
    public void deleteIngredient(Long id) {
        ingredientsRepository.deleteById(id);
    }
}
