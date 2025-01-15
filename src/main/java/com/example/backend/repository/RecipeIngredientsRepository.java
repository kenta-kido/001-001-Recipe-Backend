package com.example.backend.repository;

import com.example.backend.entity.RecipeIngredientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredientsEntity, Long> {
    List<RecipeIngredientsEntity> findByRecipeRecipeId(Long recipeId); // 特定のレシピに関連する材料を取得
}
