package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity representing a tag.
 * A tag is used to categorize recipes and can have synonyms for better searchability.
 */
@Entity
@Getter
@Setter
@Table(name = "Tag")
public class TagEntity {

    /**
     * The unique identifier for the tag.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    /**
     * The name of the tag.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The category of the tag (e.g., cuisine, dietary preference).
     * Cannot be null.
     */
    @Column(nullable = false)
    private String category;

    /**
     * A list of synonyms associated with this tag.
     * One tag can have multiple synonyms.
     * Cascade operations ensure that synonyms are removed when the tag is deleted.
     */
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TagSynonymEntity> synonyms = new ArrayList<>();

    /**
     * A list of recipes associated with this tag.
     * Cascade operations ensure that the tag-recipe relationships are removed when the tag is deleted.
     */
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TagRecipeEntity> tagRecipes;
}
