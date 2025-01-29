package com.example.backend.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a unit of measurement.
 * This entity defines various measurement units (e.g., grams, kilograms, milliliters)
 * used in recipes.
 */
@Entity
@Getter
@Setter
@Table(name = "Unit")
public class UnitEntity {

    /**
     * The unique identifier for the unit.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    /**
     * The name of the measurement unit (e.g., g, kg, mL).
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String unitName;

    /**
     * A list of recipe-ingredient relationships that use this unit.
     * Cascade operations ensure that references are removed when the unit is deleted.
     */
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonIgnore
    private List<RecipeIngredientsEntity> recipeIngredients;
}
