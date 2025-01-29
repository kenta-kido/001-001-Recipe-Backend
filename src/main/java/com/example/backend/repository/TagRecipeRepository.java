package com.example.backend.repository;

import com.example.backend.entity.TagRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link TagRecipeEntity} persistence.
 * This interface provides database operations for handling the many-to-many relationship 
 * between recipes and tags.
 */
@Repository
public interface TagRecipeRepository extends JpaRepository<TagRecipeEntity, Long> {

    /**
     * Retrieves a list of tag-recipe relationships associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe.
     * @return A list of {@link TagRecipeEntity} objects linked to the given recipe.
     */
    List<TagRecipeEntity> findByRecipeRecipeId(Long recipeId);

    /**
     * Retrieves a list of tag-recipe relationships associated with a specific tag.
     *
     * @param tagId The ID of the tag.
     * @return A list of {@link TagRecipeEntity} objects linked to the given tag.
     */
    List<TagRecipeEntity> findByTagTagId(Long tagId);
}
