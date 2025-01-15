package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "RecipeIngredients")
public class RecipeIngredientsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeIngredientsId;

    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false)
    private RecipeEntity recipe;

    @ManyToOne
    @JoinColumn(name = "ingredientId", nullable = false)
    private IngredientsEntity ingredient;

    @Column(nullable = false)
    private Integer servings;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne // Unit（ex: g, kg, mL）
    @JoinColumn(name = "unitId", nullable = false)
    private UnitEntity unit;
}
