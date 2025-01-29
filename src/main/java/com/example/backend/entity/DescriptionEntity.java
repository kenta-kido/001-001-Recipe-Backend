package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a description associated with a recipe.
 * This entity stores step-by-step instructions or additional details about a recipe.
 */
@Entity
@Getter
@Setter
@Table(name = "Description")
public class DescriptionEntity {

    /**
     * The unique identifier for the description.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long descriptionId;

    /**
     * The recipe to which this description belongs.
     * Many descriptions can be associated with one recipe.
     */
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false) // Foreign key reference to RecipeEntity
    @JsonBackReference
    private RecipeEntity recipe;

    /**
     * An optional photo associated with this description.
     * Each description can have one related photo.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "photoId", nullable = true)
    private PhotoEntity photo;

    /**
     * The textual content of the description.
     * Cannot be null and has a maximum length of 1000 characters.
     */
    @Column(nullable = false, length = 1000)
    private String description;

    /**
     * The sequence number to define the order of the descriptions within a recipe.
     */
    @Column(nullable = false)
    private Integer sequence;
}
