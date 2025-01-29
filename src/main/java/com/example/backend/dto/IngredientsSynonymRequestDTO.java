package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for requesting the creation of an ingredient synonym.
 * This class is used to transfer data related to ingredient synonyms.
 */
@Getter
@Setter
public class IngredientsSynonymRequestDTO {
    
    /**
     * The name of the synonym for an ingredient.
     */
    private String synonymName;
}
