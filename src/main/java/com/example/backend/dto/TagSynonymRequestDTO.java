package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for requesting the creation of a tag synonym.
 * This class is used to transfer data related to tag synonyms.
 */
@Getter
@Setter
public class TagSynonymRequestDTO {

    /**
     * The synonym name for a tag.
     */
    private String synonym;
}
