package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a synonym for a tag.
 * This entity allows alternative names to be associated with a tag
 * for improved searchability and categorization.
 */
@Entity
@Getter
@Setter
@Table(name = "TagSynonym")
public class TagSynonymEntity {

    /**
     * The unique identifier for the synonym.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long synonymId;

    /**
     * The tag associated with this synonym.
     * Many synonyms can belong to one tag.
     */
    @ManyToOne
    @JoinColumn(name = "tagId", nullable = false) // Foreign key reference to TagEntity
    private TagEntity tag;

    /**
     * The synonym (alternative name) for the tag.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String synonym;
}
