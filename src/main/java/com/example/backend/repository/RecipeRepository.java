package com.example.backend.repository;
import com.example.backend.entity.IngredientsEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.entity.TagEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    boolean existsByTitle(String title);
    List<RecipeEntity> findByPhotoPhotoId(Long photoId);
        // レシピIDからPhotoEntityを取得
    RecipeEntity findByRecipeId(Long recipeId);
    List<RecipeEntity> findByUserId(Long userId);    

    List<RecipeEntity> findTop3ByOrderByTimestampDesc();

    @Query("SELECT DISTINCT r FROM RecipeEntity r " +
        "JOIN r.recipeIngredients ri " +
        "WHERE ri.ingredient IN :ingredients")
    List<RecipeEntity> findByIngredientsIn(List<IngredientsEntity> ingredients);

    @Query("SELECT DISTINCT r FROM RecipeEntity r " +
        "JOIN TagRecipeEntity tr ON tr.recipe = r " +
        "WHERE tr.tag IN :tags")
    List<RecipeEntity> findByTagsIn(List<TagEntity> tags);

    @Query("SELECT r FROM RecipeEntity r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<RecipeEntity> findByTitleLike(@Param("keyword") String keyword);

}