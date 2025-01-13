package com.example.backend.service;
import com.example.backend.entity.DescriptionEntity;
import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.DescriptionRepository;
import com.example.backend.repository.PhotoRepository;
import com.example.backend.repository.RecipeRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    // レシピIDから対応するPhotoEntityを取得
    public Optional<PhotoEntity> getPhotoByRecipeId(Long recipeId) {
        return Optional.ofNullable(recipeRepository.findByRecipeId(recipeId).getPhoto());
    }

    public List<RecipeEntity> getRecipesByUserId(Long userId) {
        return recipeRepository.findByUserId(userId);
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
        recipe.setTitle(recipeDetails.getTitle());
        recipe.setTimestamp(recipeDetails.getTimestamp());

        return recipeRepository.save(recipe);
    }
    
    public RecipeEntity updateRecipePhoto(Long recipeId, PhotoEntity newPhoto) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setPhoto(newPhoto);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipeRepository.delete(recipe);
    }

    public RecipeEntity deleteRecipePhoto(Long recipeId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setPhoto(null);
        return recipeRepository.save(recipe);
    }

}
