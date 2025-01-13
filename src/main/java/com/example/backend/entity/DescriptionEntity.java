package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JsonBackReference
    private RecipeEntity recipe; // リレーションとして設定

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "photoId", nullable = true)
    private PhotoEntity photo; // 説明用画像

    @Column(nullable = false)
    private String description; // 説明内容

    @Column(nullable = false)
    private Integer sequence; // 説明の順序
}
