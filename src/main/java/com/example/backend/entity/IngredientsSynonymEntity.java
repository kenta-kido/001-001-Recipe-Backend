package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "IngredientsSynonym")
public class IngredientsSynonymEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long synonymId;

    @ManyToOne
    @JoinColumn(name = "ingredientsId", nullable = false)
    private IngredientsEntity ingredient;

    @Column(nullable = false)
    private String synonym; // Synonym (別名)
}
