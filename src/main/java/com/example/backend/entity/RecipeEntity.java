package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    
 
    @OneToOne(cascade = CascadeType.ALL) // 1つのRecipeが1つのPhotoを持つ
    @JoinColumn(name = "photo_id", referencedColumnName = "photoId", nullable = true)
    private PhotoEntity photo; // サムネイル画像用リレーション
    
    @Column(nullable = false, length = 255)
    private String title; // レシピのタイトル

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DescriptionEntity> descriptions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonIgnore
    private List<RecipeIngredientsEntity> recipeIngredients;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TagRecipeEntity> tagRecipes; // タグリスト
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