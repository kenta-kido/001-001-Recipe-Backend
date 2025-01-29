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

    /**
     * Search for synonyms using a keyword.
     *
     * @param keyword The keyword to search synonyms for.
     * @return A list of IngredientsSynonymEntity objects matching the search keyword.
     */
    @GetMapping("/synonyms/search")
    public List<IngredientsSynonymEntity> searchSynonyms(@RequestParam String keyword) {
        return ingredientsSynonymService.searchBySynonym(keyword);
    }

    /**
     * Get all synonyms associated with a specific ingredient.
     *
     * @param ingredientId The ID of the ingredient whose synonyms are being retrieved.
     * @return A ResponseEntity containing a list of IngredientsSynonymEntity objects.
     */
    @GetMapping("/{ingredientId}/synonyms")
    public ResponseEntity<List<IngredientsSynonymEntity>> getSynonymsByIngredientId(
            @PathVariable Long ingredientId) {
        return ResponseEntity.ok(ingredientsSynonymService.getSynonymsByIngredientId(ingredientId));
    }

    /**
     * Add a new synonym to a specific ingredient.
     *
     * @param ingredientId The ID of the ingredient to which the synonym will be added.
     * @param requestDTO   A DTO containing the synonym name to be added.
     * @return A ResponseEntity containing the created IngredientsSynonymEntity.
     */
    @PostMapping("/{ingredientId}/synonyms")
    public ResponseEntity<IngredientsSynonymEntity> addSynonymToIngredient(
            @PathVariable Long ingredientId,
            @RequestBody IngredientsSynonymRequestDTO requestDTO) {
        IngredientsSynonymEntity synonym = ingredientsSynonymService.addSynonymToIngredient(ingredientId, requestDTO.getSynonymName());
        return ResponseEntity.ok(synonym);
    }

    /**
     * Create a new synonym.
     *
     * @param synonym The IngredientsSynonymEntity object to be created.
     * @return A ResponseEntity containing the created IngredientsSynonymEntity.
     */
    @PostMapping("/synonyms")
    public ResponseEntity<IngredientsSynonymEntity> createSynonym(@RequestBody IngredientsSynonymEntity synonym) {
        return ResponseEntity.ok(ingredientsSynonymService.createSynonym(synonym));
    }

    /**
     * Delete a synonym by its ID.
     *
     * @param id The ID of the synonym to delete.
     * @return A ResponseEntity with no content upon successful deletion.
     */
    @DeleteMapping("/synonyms/{id}")
    public ResponseEntity<Void> deleteSynonym(@PathVariable Long id) {
        ingredientsSynonymService.deleteSynonym(id);
        return ResponseEntity.noContent().build();
    }
}