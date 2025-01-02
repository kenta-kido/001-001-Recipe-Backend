package com.example.backend.service;

import com.example.backend.entity.DescriptionEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.repository.DescriptionRepository;
import com.example.backend.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescriptionService {

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public List<DescriptionEntity> getDescriptionsByRecipeId(Long recipeId) {
        return descriptionRepository.findByRecipeRecipeId(recipeId);
    }

    public DescriptionEntity createDescription(Long recipeId, DescriptionEntity description) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        description.setRecipe(recipe);
        return descriptionRepository.save(description);
    }

    public DescriptionEntity updateDescription(Long id, DescriptionEntity descriptionDetails) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        description.setDescription(descriptionDetails.getDescription());
        description.setSequence(descriptionDetails.getSequence());
        return descriptionRepository.save(description);
    }

    public void deleteDescription(Long id) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        descriptionRepository.delete(description);
    }
}
