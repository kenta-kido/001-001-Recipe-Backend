package com.example.backend.service;

import com.example.backend.entity.PhotoEntity;
import com.example.backend.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for managing photos.
 * This service provides CRUD operations for {@link PhotoEntity}.
 */
@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    /**
     * Retrieves all photos from the database.
     *
     * @return A list of all {@link PhotoEntity} objects.
     */
    public List<PhotoEntity> getAllPhotos() {
        return photoRepository.findAll();
    }

    /**
     * Retrieves a photo by its ID.
     *
     * @param photoId The ID of the photo.
     * @return The {@link PhotoEntity} if found.
     * @throws RuntimeException if the photo is not found.
     */
    public PhotoEntity getPhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
    }

    /**
     * Creates and saves a new photo in the database.
     *
     * @param photo The photo entity to create.
     * @return The saved {@link PhotoEntity}.
     */
    public PhotoEntity createPhoto(PhotoEntity photo) {
        return photoRepository.save(photo);
    }

    /**
     * Updates an existing photo.
     *
     * @param descriptionId The ID of the description the photo belongs to.
     * @param photoId The ID of the photo to update.
     * @param photoDetails The new photo details.
     * @return The updated {@link PhotoEntity}.
     * @throws RuntimeException if the photo is not found or does not belong to the specified description.
     */
    public PhotoEntity updatePhoto(Long descriptionId, Long photoId, PhotoEntity photoDetails) {
        PhotoEntity photo = photoRepository.findById(photoId)
                // Uncomment the filter when a relation to DescriptionEntity exists
                // .filter(p -> p.getDescription().getDescriptionId().equals(descriptionId))
                .orElseThrow(() -> new RuntimeException("Photo not found or does not belong to the specified description"));

        // Update the photo content (Base64-encoded string)
        photo.setBinaryPhoto(photoDetails.getBinaryPhoto());

        return photoRepository.save(photo);
    }

    /**
     * Deletes a photo by its ID.
     *
     * @param photoId The ID of the photo to delete.
     * @throws RuntimeException if the photo is not found.
     */
    public void deletePhoto(Long photoId) {
        PhotoEntity photo = photoRepository.findById(photoId)
                // Uncomment the filter when a relation to DescriptionEntity exists
                // .filter(p -> p.getDescription().getDescriptionId().equals(descriptionId))
                .orElseThrow(() -> new RuntimeException("Photo not found or does not belong to the specified description"));

        photoRepository.delete(photo);
    }

    /**
     * Saves a photo entity to the database.
     *
     * @param photo The photo entity to save.
     * @return The saved {@link PhotoEntity}.
     */
    public PhotoEntity savePhoto(PhotoEntity photo) {
        return photoRepository.save(photo);
    }
}
