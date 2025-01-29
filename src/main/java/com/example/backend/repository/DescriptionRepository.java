package com.example.backend.repository;

import com.example.backend.entity.DescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link DescriptionEntity} persistence.
 * This interface provides database operations for descriptions associated with recipes.
 */
@Repository
public interface DescriptionRepository extends JpaRepository<DescriptionEntity, Long> {

    /**
     * Retrieves a list of descriptions associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe whose descriptions are to be retrieved.
     * @return A list of {@link DescriptionEntity} objects related to the given recipe.
     */
    List<DescriptionEntity> findByRecipeRecipeId(Long recipeId);

    /**
     * Retrieves a list of descriptions associated with a specific photo.
     *
     * @param photoId The ID of the photo linked to the descriptions.
     * @return A list of {@link DescriptionEntity} objects related to the given photo.
     */
    List<DescriptionEntity> findByPhotoPhotoId(Long photoId);

    // Uncomment this method if sequence validation is needed
    // boolean existsByRecipeAndSequence(RecipeEntity recipe, int sequence);
}
