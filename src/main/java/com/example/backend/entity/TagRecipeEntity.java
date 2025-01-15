package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TagRecipe")
public class TagRecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagRecipeId;

    @ManyToOne
    @JoinColumn(name = "tagId", nullable = false)
    private TagEntity tag;

    @ManyToOne
    @JoinColumn(name = "recipeId", nullable = false)
    private RecipeEntity recipe;
}
