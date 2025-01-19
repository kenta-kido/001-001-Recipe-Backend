package com.example.backend.service;

import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.repository.IngredientsSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsSynonymService {

    @Autowired
    private IngredientsSynonymRepository ingredientsSynonymRepository;

    public List<IngredientsSynonymEntity> searchBySynonym(String synonym) {
        return ingredientsSynonymRepository.findBySynonymContainingIgnoreCase(synonym);
    }

    public IngredientsSynonymEntity createSynonym(IngredientsSynonymEntity synonym) {
        return ingredientsSynonymRepository.save(synonym);
    }

    public void deleteSynonym(Long id) {
        ingredientsSynonymRepository.deleteById(id);
    }
}
