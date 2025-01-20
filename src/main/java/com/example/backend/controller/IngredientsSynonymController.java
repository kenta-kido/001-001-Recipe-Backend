package com.example.backend.controller;

import com.example.backend.dto.IngredientsSynonymRequestDTO;
import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.service.IngredientsSynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientsSynonymController {

    @Autowired
    private IngredientsSynonymService ingredientsSynonymService;

    @GetMapping("/synonyms/search")
    public List<IngredientsSynonymEntity> searchSynonyms(@RequestParam String keyword) {
        return ingredientsSynonymService.searchBySynonym(keyword);
    }
    // 材料に紐付くシノニムを取得
    @GetMapping("/{ingredientId}/synonyms")
    public ResponseEntity<List<IngredientsSynonymEntity>> getSynonymsByIngredientId(
            @PathVariable Long ingredientId) {
        return ResponseEntity.ok(ingredientsSynonymService.getSynonymsByIngredientId(ingredientId));
    }

    // 材料にシノニムを追加
    @PostMapping("/{ingredientId}/synonyms")
    public ResponseEntity<IngredientsSynonymEntity> addSynonymToIngredient(
            @PathVariable Long ingredientId,
            @RequestBody IngredientsSynonymRequestDTO requestDTO) {
        IngredientsSynonymEntity synonym = ingredientsSynonymService.addSynonymToIngredient(ingredientId, requestDTO.getSynonymName());
        return ResponseEntity.ok(synonym);
    }
    
    @PostMapping("/synonyms")
    public ResponseEntity<IngredientsSynonymEntity> createSynonym(@RequestBody IngredientsSynonymEntity synonym) {
        return ResponseEntity.ok(ingredientsSynonymService.createSynonym(synonym));
    }

    @DeleteMapping("/synonyms/{id}")
    public ResponseEntity<Void> deleteSynonym(@PathVariable Long id) {
        ingredientsSynonymService.deleteSynonym(id);
        return ResponseEntity.noContent().build();
    }
}
