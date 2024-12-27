package com.example.backend.service;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.RecipeRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<RecipeEntity> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<RecipeEntity> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public RecipeEntity createRecipe(RecipeEntity recipe, Long userId) {
        // データベースからユーザーを取得
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ユーザーのロールを確認
        if (!"ROLE_USER".equals(user.getRole())) {
            throw new RuntimeException("Unauthorized: Only ROLE_USER can create recipes");
        }

        // ユーザーをレシピに設定
        recipe.setUser(user);

        // レシピを保存
        return recipeRepository.save(recipe);
    }

    public RecipeEntity updateRecipe(Long id, RecipeEntity recipeDetails) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        
        recipe.setUser(recipeDetails.getUser());
        recipe.setDescriptionId(recipeDetails.getDescriptionId());
        recipe.setTimestamp(recipeDetails.getTimestamp());

        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipeRepository.delete(recipe);
    }
}
