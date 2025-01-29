package com.example.backend.service;

import com.example.backend.entity.DescriptionEntity;
import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.repository.DescriptionRepository;
import com.example.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

/**
 * Service for managing recipe descriptions.
 * This service handles CRUD operations related to {@link DescriptionEntity} 
 * and its associated photos.
 */
@Service
public class DescriptionService {

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private PhotoService photoService;

    /**
     * Retrieves all descriptions associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe.
     * @return A list of {@link DescriptionEntity} associated with the given recipe.
     */
    public List<DescriptionEntity> getDescriptionsByRecipeId(Long recipeId) {
        return descriptionRepository.findByRecipeRecipeId(recipeId);
    }

    /**
     * Retrieves all descriptions associated with a specific photo.
     *
     * @param photoId The ID of the photo.
     * @return A list of {@link DescriptionEntity} associated with the given photo.
     */
    public List<DescriptionEntity> getDescriptionsByPhotoId(Long photoId) {
        return descriptionRepository.findByPhotoPhotoId(photoId);
    }

    /**
     * Retrieves the photo associated with a specific description.
     *
     * @param descriptionId The ID of the description.
     * @return An {@link Optional} containing the {@link PhotoEntity}, if present.
     * @throws RuntimeException if the description is not found.
     */
    public Optional<PhotoEntity> getPhotoByDescriptionId(Long descriptionId) {
        return Optional.ofNullable(descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new RuntimeException("Description not found"))
                .getPhoto());
    }

    /**
     * Creates a new description for a specific recipe, optionally with a photo.
     *
     * @param recipeId The ID of the recipe.
     * @param description The description entity to be created.
     * @return The saved {@link DescriptionEntity}.
     * @throws RuntimeException if the recipe is not found.
     */
    public DescriptionEntity createDescriptionWithPhoto(Long recipeId, DescriptionEntity description) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        description.setRecipe(recipe);

        // Save photo if provided
        if (description.getPhoto() != null) {
            PhotoEntity savedPhoto = photoService.savePhoto(description.getPhoto());
            description.setPhoto(savedPhoto);
        }

        return descriptionRepository.save(description);
    }

    /**
     * Updates an existing description.
     *
     * @param id The ID of the description to update.
     * @param descriptionDetails The updated description details.
     * @return The updated {@link DescriptionEntity}.
     * @throws RuntimeException if the description is not found.
     */
    public DescriptionEntity updateDescription(Long id, DescriptionEntity descriptionDetails) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        description.setDescription(descriptionDetails.getDescription());
        description.setSequence(descriptionDetails.getSequence());
        return descriptionRepository.save(description);
    }

    /**
     * Updates the photo associated with a specific description.
     *
     * @param id The ID of the description.
     * @param newPhoto The new photo entity.
     * @return The updated {@link DescriptionEntity}.
     * @throws RuntimeException if the description is not found.
     */
    public DescriptionEntity updateDescriptionPhoto(Long id, PhotoEntity newPhoto) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        PhotoEntity savedPhoto = photoService.savePhoto(newPhoto);
        description.setPhoto(savedPhoto);

        return descriptionRepository.save(description);
    }

    /**
     * Deletes a description and its associated photo (if present).
     *
     * @param id The ID of the description to delete.
     * @throws RuntimeException if the description is not found.
     */
    public void deleteDescription(Long id) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        // Delete associated photo
        if (description.getPhoto() != null) {
            photoService.deletePhoto(description.getPhoto().getPhotoId());
        }

        descriptionRepository.delete(description);
    }

    /**
     * Deletes the photo associated with a specific description, but keeps the description itself.
     *
     * @param id The ID of the description.
     * @throws RuntimeException if the description is not found.
     */
    public void deleteDescriptionPhoto(Long id) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        if (description.getPhoto() != null) {
            photoService.deletePhoto(description.getPhoto().getPhotoId());
            description.setPhoto(null);
            descriptionRepository.save(description);
        }
    }
}
