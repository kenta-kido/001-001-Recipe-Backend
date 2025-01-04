package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false, length = 255)
    private String title; // レシピのタイトル

    // @Column(nullable = false)
    // private Long descriptionId; // Foreign Key for descriptions

    @Column(nullable = false)
    private LocalDateTime timestamp; // Timestamp

    // 保存前にタイムスタンプを現在時刻に設定
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // 必要なら更新時にもタイムスタンプを変更
    @PreUpdate
    protected void onUpdate() {
        this.timestamp = LocalDateTime.now();
    }
}