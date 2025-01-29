package com.example.backend.controller;

import com.example.backend.entity.IngredientsEntity;
import com.example.backend.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    /**
     * Fetch all ingredients from the database.
     *
     * @return A list of all IngredientsEntity objects.
     */
    @GetMapping
    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsService.getAllIngredients();
    }

    /**
     * Create a new ingredient in the database.
     *
     * @param ingredient The ingredient entity to be created.
     * @return A ResponseEntity containing the created IngredientsEntity object.
     */
    @PostMapping
    public ResponseEntity<IngredientsEntity> createIngredient(@RequestBody IngredientsEntity ingredient) {
        return ResponseEntity.ok(ingredientsService.createIngredient(ingredient));
    }

    /**
     * Delete an ingredient by its ID.
     *
     * @param id The ID of the ingredient to delete.
     * @return A ResponseEntity with no content upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientsService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}