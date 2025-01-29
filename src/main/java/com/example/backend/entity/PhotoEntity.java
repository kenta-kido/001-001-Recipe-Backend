package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a photo.
 * This entity stores image data in Base64 format.
 */
@Entity
@Getter
@Setter
@Table(name = "photo")
public class PhotoEntity {

    /**
     * The unique identifier for the photo.
     * Primary Key, auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    /**
     * The Base64-encoded image data.
     * Stored as a TEXT column to accommodate large image data.
     * Cannot be null.
     */
    @Column(name = "binary_photo", columnDefinition = "TEXT", nullable = false)
    private String binaryPhoto;
}
