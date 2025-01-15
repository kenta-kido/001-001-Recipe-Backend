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

    @GetMapping
    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsService.getAllIngredients();
    }

    @PostMapping
    public ResponseEntity<IngredientsEntity> createIngredient(@RequestBody IngredientsEntity ingredient) {
        return ResponseEntity.ok(ingredientsService.createIngredient(ingredient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientsService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
