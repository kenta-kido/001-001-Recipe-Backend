package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "Recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId; // Primary Key

    @ManyToOne // Many Recipes belong to one User
    @JoinColumn(name = "user_id", nullable = false) // 外部キーを指定
    private UserEntity user; // リレーションとして設定

    @Column(nullable = false)
    private Long descriptionId; // Foreign Key for descriptions

    @Column(nullable = false)
    private LocalDateTime timestamp; // Timestamp

    // Getters and Setters
}