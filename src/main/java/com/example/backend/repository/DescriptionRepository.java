package com.example.backend.repository;

import com.example.backend.entity.DescriptionEntity;
import com.example.backend.entity.RecipeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<DescriptionEntity, Long> {
    List<DescriptionEntity> findByRecipeRecipeId(Long recipeId); // 特定のレシピに関連する説明を取得
    boolean existsByRecipeAndSequence(RecipeEntity recipe, int sequence);
}
