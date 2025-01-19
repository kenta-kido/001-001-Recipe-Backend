package com.example.backend.service;

import com.example.backend.entity.IngredientsEntity;
import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.repository.IngredientsRepository;
import com.example.backend.repository.IngredientsSynonymRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsService {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private IngredientsSynonymRepository ingredientsSynonymRepository;

    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsRepository.findAll();
    }

    public IngredientsEntity getIngredientById(Long id) {
        return ingredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    public IngredientsEntity createIngredient(IngredientsEntity ingredient) {
        // 材料の重複をチェック
        if (ingredientsRepository.existsByName(ingredient.getName())) {
            throw new RuntimeException("Ingredient already exists");
        }

        // 材料を保存
        IngredientsEntity savedIngredient = ingredientsRepository.save(ingredient);

        // デフォルトのシノニムを登録
        IngredientsSynonymEntity synonym = new IngredientsSynonymEntity();
        synonym.setIngredient(savedIngredient);
        synonym.setSynonym(ingredient.getName()); // デフォルトで名前をシノニムとして使用

        ingredientsSynonymRepository.save(synonym);
        return savedIngredient;
    }

    public void deleteIngredient(Long id) {
        ingredientsRepository.deleteById(id);
    }
}
