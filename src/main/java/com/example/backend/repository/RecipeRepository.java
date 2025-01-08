package com.example.backend.repository;
import com.example.backend.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    boolean existsByTitle(String title);
    List<RecipeEntity> findByPhotoPhotoId(Long photoId);
        // レシピIDからPhotoEntityを取得
        RecipeEntity findByRecipeId(Long recipeId);
}