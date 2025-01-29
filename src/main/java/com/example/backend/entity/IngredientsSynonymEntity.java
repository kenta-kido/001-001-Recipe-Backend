package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a synonym for an ingredient.
 * This entity stores alternative names for a specific ingredient.
 */
@Entity
@Getter
@Setter
@Table(name = "IngredientsSynonym")
public class IngredientsSynonymEntity {

    /**
     * The unique identifier for the synonym.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long synonymId;

    /**
     * The ingredient associated with this synonym.
     * Many synonyms can be linked to one ingredient.
     */
    @ManyToOne
    @JoinColumn(name = "ingredientsId", nullable = false) // Foreign key reference to IngredientsEntity
    private IngredientsEntity ingredient;

    /**
     * The synonym (alternative name) of the ingredient.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String synonym;
}
