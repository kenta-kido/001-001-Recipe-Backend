package com.example.backend.service;

import com.example.backend.entity.IngredientsEntity;
import com.example.backend.repository.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsService {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    public List<IngredientsEntity> getAllIngredients() {
        return ingredientsRepository.findAll();
    }

    public IngredientsEntity getIngredientById(Long id) {
        return ingredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    public IngredientsEntity createIngredient(IngredientsEntity ingredient) {
        if (ingredientsRepository.existsByName(ingredient.getName())) {
            throw new RuntimeException("Ingredient already exists");
        }
        return ingredientsRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        ingredientsRepository.deleteById(id);
    }
}
