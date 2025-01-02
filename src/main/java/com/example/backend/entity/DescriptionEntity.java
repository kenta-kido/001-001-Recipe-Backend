package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Description")
public class DescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long descriptionId; // Primary Key

    @ManyToOne // Many Descriptions belong to one Recipe
    @JoinColumn(name = "recipe_id", nullable = false) // 外部キーを指定
    private RecipeEntity recipe; // リレーションとして設定

    @Column(nullable = false)
    private String description; // 説明内容

    @Column(nullable = false)
    private Integer sequence; // 説明の順序
}
