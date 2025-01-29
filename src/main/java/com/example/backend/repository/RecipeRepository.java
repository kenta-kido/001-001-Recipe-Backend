package com.example.backend.repository;

import com.example.backend.entity.IngredientsEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link RecipeEntity} persistence.
 * This interface provides database operations for handling recipe entities.
 */
@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    /**
     * Checks if a recipe with the given title already exists.
     *
     * @param title The title of the recipe to check.
     * @return {@code true} if a recipe with the specified title exists, otherwise {@code false}.
     */
    boolean existsByTitle(String title);

    /**
     * Retrieves a list of recipes associated with a specific photo.
     *
     * @param photoId The ID of the photo.
     * @return A list of {@link RecipeEntity} objects linked to the given photo.
     */
    List<RecipeEntity> findByPhotoPhotoId(Long photoId);

    /**
     * Retrieves a recipe by its ID.
     *
     * @param recipeId The ID of the recipe to retrieve.
     * @return The {@link RecipeEntity} object with the given ID.
     */
    RecipeEntity findByRecipeId(Long recipeId);

    /**
     * Retrieves a list of recipes created by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of {@link RecipeEntity} objects created by the given user.
     */
    List<RecipeEntity> findByUserId(Long userId);

    /**
     * Retrieves the latest three recipes ordered by timestamp in descending order.
     *
     * @return A list of the top three latest {@link RecipeEntity} objects.
     */
    List<RecipeEntity> findTop3ByOrderByTimestampDesc();

    /**
     * Retrieves a list of recipes that contain any of the specified ingredients.
     *
     * @param ingredients A list of {@link IngredientsEntity} objects.
     * @return A list of {@link RecipeEntity} objects containing the specified ingredients.
     */
    @Query("SELECT DISTINCT r FROM RecipeEntity r " +
        "JOIN r.recipeIngredients ri " +
        "WHERE ri.ingredient IN :ingredients")
    List<RecipeEntity> findByIngredientsIn(@Param("ingredients") List<IngredientsEntity> ingredients);

    /**
     * Retrieves a list of recipes that contain any of the specified tags.
     *
     * @param tags A list of {@link TagEntity} objects.
     * @return A list of {@link RecipeEntity} objects tagged with the specified tags.
     */
    @Query("SELECT DISTINCT r FROM RecipeEntity r " +
        "JOIN TagRecipeEntity tr ON tr.recipe = r " +
        "WHERE tr.tag IN :tags")
    List<RecipeEntity> findByTagsIn(@Param("tags") List<TagEntity> tags);

    /**
     * Searches for recipes with titles containing the given keyword (case insensitive).
     *
     * @param keyword The search keyword.
     * @return A list of {@link RecipeEntity} objects matching the keyword in their title.
     */
    @Query("SELECT r FROM RecipeEntity r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<RecipeEntity> findByTitleLike(@Param("keyword") String keyword);
}
