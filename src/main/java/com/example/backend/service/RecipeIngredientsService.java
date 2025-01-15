package com.example.backend.service;

import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientsService {

    @Autowired
    private RecipeIngredientsRepository recipeIngredientsRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientsRepository ingredientRepository;

    @Autowired
    private UnitRepository unitRepository;

    // 特定のレシピに関連する材料を取得
    public List<RecipeIngredientsEntity> getIngredientsByRecipeId(Long recipeId) {
        return recipeIngredientsRepository.findByRecipeRecipeId(recipeId);
    }

    // 特定のレシピに材料を追加
    public RecipeIngredientsEntity addIngredientToRecipe(Long recipeId, RecipeIngredientsEntity recipeIngredient) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        IngredientsEntity ingredient = ingredientRepository.findById(recipeIngredient.getIngredient().getIngredientId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        UnitEntity unit = unitRepository.findById(recipeIngredient.getUnit().getUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setUnit(unit);

        return recipeIngredientsRepository.save(recipeIngredient);
    }

    // 特定の材料を更新
    public RecipeIngredientsEntity updateIngredient(Long id, RecipeIngredientsEntity updatedIngredient) {
        RecipeIngredientsEntity existingIngredient = recipeIngredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RecipeIngredient not found"));

        existingIngredient.setQuantity(updatedIngredient.getQuantity());
        existingIngredient.setServings(updatedIngredient.getServings());
        existingIngredient.setUnit(updatedIngredient.getUnit());
        existingIngredient.setIngredient(updatedIngredient.getIngredient());

        return recipeIngredientsRepository.save(existingIngredient);
    }

    // 特定の材料を削除
    public void deleteIngredient(Long id) {
        RecipeIngredientsEntity ingredient = recipeIngredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RecipeIngredient not found"));

        recipeIngredientsRepository.delete(ingredient);
    }
}
