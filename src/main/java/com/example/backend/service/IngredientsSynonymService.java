package com.example.backend.service;

import com.example.backend.entity.IngredientsEntity;
import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.repository.IngredientsRepository;
import com.example.backend.repository.IngredientsSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsSynonymService {

    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private IngredientsSynonymRepository ingredientsSynonymRepository;

    public List<IngredientsSynonymEntity> searchBySynonym(String synonym) {
        return ingredientsSynonymRepository.findBySynonymContainingIgnoreCase(synonym);
    }
    public List<IngredientsSynonymEntity> getSynonymsByIngredientId(Long ingredientId) {
        return ingredientsSynonymRepository.findByIngredientIngredientId(ingredientId);
    }
    public IngredientsSynonymEntity addSynonymToIngredient(Long ingredientId, String synonymName) {
        // 材料を取得
        IngredientsEntity ingredient = ingredientsRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    
        // シノニムを作成
        IngredientsSynonymEntity synonym = new IngredientsSynonymEntity();
        synonym.setIngredient(ingredient);
        synonym.setSynonym(synonymName);
    
        // シノニムを保存
        return ingredientsSynonymRepository.save(synonym);
    }
    public IngredientsSynonymEntity createSynonym(IngredientsSynonymEntity synonym) {
        return ingredientsSynonymRepository.save(synonym);
    }

    public void deleteSynonym(Long id) {
        ingredientsSynonymRepository.deleteById(id);
    }
}
