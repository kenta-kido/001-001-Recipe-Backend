package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "photo")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId; // プライマリキー

    @ManyToOne
    @JoinColumn(name = "description_id", nullable = false) // Descriptionテーブルとのリレーション
    private DescriptionEntity description; // 外部キー

    @Column(name = "binary_photo", columnDefinition = "TEXT", nullable = false)
    private String binaryPhoto; // Base64エンコードされた写真データ (文字列)

    @Column(name = "sequence", nullable = false)
    private int sequence; // 順序
}
