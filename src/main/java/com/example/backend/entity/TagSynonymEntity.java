package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TagSynonym")
public class TagSynonymEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long synonymId;

    @ManyToOne
    @JoinColumn(name = "tagId", nullable = false)
    private TagEntity tag;

    @Column(nullable = false)
    private String synonym; // Synonym (別名)
}
