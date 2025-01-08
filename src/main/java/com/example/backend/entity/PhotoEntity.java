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

    @Column(name = "binary_photo", columnDefinition = "TEXT", nullable = false)
    private String binaryPhoto; // Base64エンコードされた写真データ (文字列)
}
