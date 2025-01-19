package com.example.backend.controller;

import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.service.IngredientsSynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients/synonyms")
public class IngredientsSynonymController {

    @Autowired
    private IngredientsSynonymService ingredientsSynonymService;

    @GetMapping("/search")
    public List<IngredientsSynonymEntity> searchSynonyms(@RequestParam String keyword) {
        return ingredientsSynonymService.searchBySynonym(keyword);
    }

    @PostMapping
    public ResponseEntity<IngredientsSynonymEntity> createSynonym(@RequestBody IngredientsSynonymEntity synonym) {
        return ResponseEntity.ok(ingredientsSynonymService.createSynonym(synonym));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSynonym(@PathVariable Long id) {
        ingredientsSynonymService.deleteSynonym(id);
        return ResponseEntity.noContent().build();
    }
}
