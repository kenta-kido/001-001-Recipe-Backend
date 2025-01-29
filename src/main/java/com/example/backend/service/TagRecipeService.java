package com.example.backend.service;

import com.example.backend.entity.RecipeEntity;
import com.example.backend.entity.TagEntity;
import com.example.backend.entity.TagRecipeEntity;
import com.example.backend.repository.RecipeRepository;
import com.example.backend.repository.TagRecipeRepository;
import com.example.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for managing the relationship between recipes and tags.
 * This service provides CRUD operations for {@link TagRecipeEntity}.
 */
@Service
public class TagRecipeService {

    @Autowired
    private TagRecipeRepository tagRecipeRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private TagRepository tagRepository;

    /**
     * Retrieves all tags associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe.
     * @return A list of {@link TagRecipeEntity} associated with the given recipe.
     */
    public List<TagRecipeEntity> getTagsByRecipeId(Long recipeId) {
        return tagRecipeRepository.findByRecipeRecipeId(recipeId);
    }

    /**
     * Retrieves all recipes associated with a specific tag.
     *
     * @param tagId The ID of the tag.
     * @return A list of {@link TagRecipeEntity} associated with the given tag.
     */
    public List<TagRecipeEntity> getRecipesByTagId(Long tagId) {
        return tagRecipeRepository.findByTagTagId(tagId);
    }

    /**
     * Adds a tag to a recipe by creating a relationship between them.
     *
     * @param recipeId The ID of the recipe.
     * @param tagId The ID of the tag.
     * @return The saved {@link TagRecipeEntity} representing the relationship.
     * @throws RuntimeException if the recipe or tag is not found.
     */
    public TagRecipeEntity addTagToRecipe(Long recipeId, Long tagId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        TagRecipeEntity tagRecipe = new TagRecipeEntity();
        tagRecipe.setRecipe(recipe);
        tagRecipe.setTag(tag);

        return tagRecipeRepository.save(tagRecipe);
    }

    /**
     * Removes a tag from a recipe by deleting the relationship.
     *
     * @param tagRecipeId The ID of the {@link TagRecipeEntity} to delete.
     * @throws RuntimeException if the relationship is not found.
     */
    public void removeTagFromRecipe(Long tagRecipeId) {
        TagRecipeEntity tagRecipe = tagRecipeRepository.findById(tagRecipeId)
                .orElseThrow(() -> new RuntimeException("TagRecipe not found"));

        tagRecipeRepository.delete(tagRecipe);
    }
}
