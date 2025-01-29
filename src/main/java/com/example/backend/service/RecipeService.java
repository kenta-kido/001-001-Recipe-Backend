package com.example.backend.service;

import com.example.backend.entity.*;
import com.example.backend.repository.*;
import com.example.backend.util.LevenshteinDistance;
import com.example.backend.util.TrigramSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Service for managing recipes.
 * This service provides CRUD operations for {@link RecipeEntity} and implements 
 * advanced search functionalities using similarity algorithms.
 */
@Service
public class RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientsSynonymRepository ingredientsSynonymRepository;

    @Autowired
    private TagSynonymRepository tagSynonymRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private TagRepository tagRepository;

    private static final int LEVENSHTEIN_THRESHOLD = 2;
    private static final double TRIGRAM_SIMILARITY_THRESHOLD = 0.15;

    /**
     * Retrieves all recipes from the database.
     *
     * @return A list of all {@link RecipeEntity} objects.
     */
    public List<RecipeEntity> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id The ID of the recipe.
     * @return An {@link Optional} containing the {@link RecipeEntity} if found.
     */
    public Optional<RecipeEntity> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    /**
     * Retrieves the latest three recipes.
     *
     * @return A list of the three most recent {@link RecipeEntity} objects.
     */
    public List<RecipeEntity> getLatestRecipes() {
        return recipeRepository.findTop3ByOrderByTimestampDesc();
    }

    /**
     * Retrieves the photo associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe.
     * @return An {@link Optional} containing the {@link PhotoEntity}, if present.
     */
    public Optional<PhotoEntity> getPhotoByRecipeId(Long recipeId) {
        return Optional.ofNullable(recipeRepository.findByRecipeId(recipeId).getPhoto());
    }

    /**
     * Retrieves all recipes created by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of {@link RecipeEntity} created by the specified user.
     */
    public List<RecipeEntity> getRecipesByUserId(Long userId) {
        return recipeRepository.findByUserId(userId);
    }
    
    /**
     * Creates a new recipe and associates it with a user.
     *
     * @param recipe The recipe entity to be created.
     * @param userId The ID of the user creating the recipe.
     * @return The saved {@link RecipeEntity}.
     * @throws RuntimeException if the user is not found or unauthorized.
     */
    public RecipeEntity createRecipe(RecipeEntity recipe, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ROLE_USER".equals(user.getRole())) {
            throw new RuntimeException("Unauthorized: Only ROLE_USER can create recipes");
        }

        recipe.setUser(user);
        return recipeRepository.save(recipe);
    }

    /**
     * Updates an existing recipe.
     *
     * @param id The ID of the recipe to update.
     * @param recipeDetails The updated recipe details.
     * @return The updated {@link RecipeEntity}.
     * @throws RuntimeException if the recipe is not found.
     */
    public RecipeEntity updateRecipe(Long id, RecipeEntity recipeDetails) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setUser(recipeDetails.getUser());
        recipe.setTitle(recipeDetails.getTitle());
        recipe.setTimestamp(recipeDetails.getTimestamp());

        return recipeRepository.save(recipe);
    }
    
    /**
     * Updates the photo associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe.
     * @param newPhoto The new photo entity.
     * @return The updated {@link RecipeEntity}.
     * @throws RuntimeException if the recipe is not found.
     */
    public RecipeEntity updateRecipePhoto(Long recipeId, PhotoEntity newPhoto) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setPhoto(newPhoto);
        return recipeRepository.save(recipe);
    }

    /**
     * Deletes a recipe by its ID.
     *
     * @param id The ID of the recipe to delete.
     * @throws RuntimeException if the recipe is not found.
     */
    public void deleteRecipe(Long id) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipeRepository.delete(recipe);
    }

    /**
     * Removes the photo associated with a recipe.
     *
     * @param recipeId The ID of the recipe.
     * @return The updated {@link RecipeEntity} without a photo.
     * @throws RuntimeException if the recipe is not found.
     */
    public RecipeEntity deleteRecipePhoto(Long recipeId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setPhoto(null);
        return recipeRepository.save(recipe);
    }

    /**
     * Searches for recipes by a given keyword, considering ingredients, tags, and titles.
     *
     * @param keyword The search keyword.
     * @return A list of {@link RecipeEntity} matching the search criteria.
     */
    public List<RecipeEntity> findRecipesByKeyword(String keyword) {
        String[] keywords = keyword.split("\\s+");
        Set<RecipeEntity> uniqueRecipes = new HashSet<>();
    
        for (String word : keywords) {
            uniqueRecipes.addAll(findRecipesByIngredientNameWithSimilarity(word));
            uniqueRecipes.addAll(findRecipesByTagNameWithSimilarity(word));
            uniqueRecipes.addAll(findRecipesByIngredientTrigram(word));
            uniqueRecipes.addAll(findRecipesByTagTrigram(word));
            uniqueRecipes.addAll(recipeRepository.findByTitleLike("%" + word + "%"));
        }
    
        return new ArrayList<>(uniqueRecipes);
    }
    
    /**
     * Finds recipes using ingredient names and synonyms with Levenshtein similarity.
     */
    public List<RecipeEntity> findRecipesByIngredientNameWithSimilarity(String keyword) {
        List<IngredientsSynonymEntity> synonyms = ingredientsSynonymRepository.findAll();

        List<IngredientsEntity> matchingIngredients = synonyms.stream()
            .filter(synonym -> LevenshteinDistance.calculate(synonym.getSynonym(), keyword) <= LEVENSHTEIN_THRESHOLD)
            .map(IngredientsSynonymEntity::getIngredient)
            .distinct()
            .toList();

        return recipeRepository.findByIngredientsIn(matchingIngredients);
    }

    /**
     * Finds recipes using tag names and synonyms with Levenshtein similarity.
     */
    public List<RecipeEntity> findRecipesByTagNameWithSimilarity(String keyword) {
        List<TagSynonymEntity> synonyms = tagSynonymRepository.findAll();

        List<TagEntity> matchingTags = synonyms.stream()
            .filter(synonym -> LevenshteinDistance.calculate(synonym.getSynonym(), keyword) <= LEVENSHTEIN_THRESHOLD)
            .map(TagSynonymEntity::getTag)
            .distinct()
            .toList();

        return recipeRepository.findByTagsIn(matchingTags);
    }

    /**
     * Finds recipes using ingredient names with trigram similarity.
     */
    public List<RecipeEntity> findRecipesByIngredientTrigram(String keyword) {
        return recipeRepository.findByIngredientsIn(
                ingredientsRepository.findAll().stream()
                .filter(ingredient -> TrigramSimilarity.calculate(ingredient.getName(), keyword) >= TRIGRAM_SIMILARITY_THRESHOLD)
                .toList());
    }

    /**
     * Finds recipes using tag names with trigram similarity.
     */
    public List<RecipeEntity> findRecipesByTagTrigram(String keyword) {
        return recipeRepository.findByTagsIn(
                tagRepository.findAll().stream()
                .filter(tag -> TrigramSimilarity.calculate(tag.getName(), keyword) >= TRIGRAM_SIMILARITY_THRESHOLD)
                .toList());
    }
}
