package com.example.backend.controller;

import com.example.backend.entity.TagRecipeEntity;
import com.example.backend.service.TagRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class TagRecipeController {

    @Autowired
    private TagRecipeService tagRecipeService;

    /**
     * Fetch all tags associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe whose tags are to be retrieved.
     * @return A list of TagRecipeEntity objects associated with the specified recipe.
     */
    @GetMapping("/{recipeId}/tags")
    public List<TagRecipeEntity> getTagsByRecipeId(@PathVariable Long recipeId) {
        return tagRecipeService.getTagsByRecipeId(recipeId);
    }

    /**
     * Fetch all recipes associated with a specific tag.
     *
     * @param tagId The ID of the tag whose associated recipes are to be retrieved.
     * @return A list of TagRecipeEntity objects associated with the specified tag.
     */
    @GetMapping("/tag/{tagId}")
    public List<TagRecipeEntity> getRecipesByTagId(@PathVariable Long tagId) {
        return tagRecipeService.getRecipesByTagId(tagId);
    }

    /**
     * Add a tag to a specific recipe.
     *
     * @param recipeId The ID of the recipe to which the tag will be added.
     * @param tagId    The ID of the tag to associate with the recipe.
     * @return A ResponseEntity containing the created TagRecipeEntity object.
     */
    @PostMapping("/{recipeId}/tags/{tagId}")
    public ResponseEntity<TagRecipeEntity> addTagToRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long tagId
    ) {
        TagRecipeEntity tagRecipe = tagRecipeService.addTagToRecipe(recipeId, tagId);
        return ResponseEntity.ok(tagRecipe);
    }

    /**
     * Remove a tag from a recipe.
     *
     * @param tagRecipeId The ID of the TagRecipeEntity association to remove.
     * @return A ResponseEntity with no content upon successful removal.
     */
    @DeleteMapping("/{recipeId}/tags/{tagRecipeId}")
    public ResponseEntity<Void> removeTagFromRecipe(@PathVariable Long tagRecipeId) {
        tagRecipeService.removeTagFromRecipe(tagRecipeId);
        return ResponseEntity.noContent().build();
    }
}