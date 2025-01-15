package com.example.backend.controller;

import com.example.backend.entity.RecipeIngredientsEntity;
import com.example.backend.service.RecipeIngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/recipes/{recipeId}/ingredients")
public class RecipeIngredientsController {

    @Autowired
    private RecipeIngredientsService recipeIngredientsService;

    // 特定のレシピに関連する材料を取得
    @GetMapping
    public List<RecipeIngredientsEntity> getIngredientsByRecipeId(@PathVariable Long recipeId) {
        return recipeIngredientsService.getIngredientsByRecipeId(recipeId);
    }

    // 特定のレシピに材料を追加
    @PostMapping
    public ResponseEntity<RecipeIngredientsEntity> addIngredientToRecipe(
            @PathVariable Long recipeId,
            @RequestBody RecipeIngredientsEntity recipeIngredient
    ) {
        RecipeIngredientsEntity createdIngredient = recipeIngredientsService.addIngredientToRecipe(recipeId, recipeIngredient);
        return ResponseEntity.ok(createdIngredient);
    }

    // 特定の材料を更新
    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredientsEntity> updateIngredient(
            @PathVariable Long id,
            @RequestBody RecipeIngredientsEntity updatedIngredient
    ) {
        RecipeIngredientsEntity updated = recipeIngredientsService.updateIngredient(id, updatedIngredient);
        return ResponseEntity.ok(updated);
    }

    // 特定の材料を削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        recipeIngredientsService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
