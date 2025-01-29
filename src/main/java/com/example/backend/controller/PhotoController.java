package com.example.backend.controller;

import com.example.backend.entity.PhotoEntity;
import com.example.backend.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/descriptions/{descriptionId}/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    /**
     * Fetch a specific photo by its ID.
     *
     * @param descriptionId The ID of the description the photo is associated with.
     * @param photoId       The ID of the photo to retrieve.
     * @return A ResponseEntity containing the PhotoEntity if found.
     */
    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoEntity> getPhotoById(@PathVariable Long descriptionId, @PathVariable Long photoId) {
        PhotoEntity photo = photoService.getPhotoById(photoId);
        return ResponseEntity.ok(photo);
    }

    /**
     * Create a new photo and associate it with a specific description.
     *
     * @param descriptionId The ID of the description to associate the photo with.
     * @param photo         The PhotoEntity object containing the photo details to be created.
     * @return A ResponseEntity containing the created PhotoEntity.
     */
    @PostMapping
    public ResponseEntity<PhotoEntity> createPhoto(
            @PathVariable Long descriptionId,
            @RequestBody PhotoEntity photo
    ) {
        PhotoEntity createdPhoto = photoService.createPhoto(photo);
        return ResponseEntity.ok(createdPhoto);
    }

    /**
     * Update an existing photo associated with a specific description.
     *
     * @param descriptionId The ID of the description the photo is associated with.
     * @param photoId       The ID of the photo to update.
     * @param photoDetails  The updated details of the photo.
     * @return A ResponseEntity containing the updated PhotoEntity.
     */
    @PutMapping("/{photoId}")
    public ResponseEntity<PhotoEntity> updatePhoto(
            @PathVariable Long descriptionId,
            @PathVariable Long photoId,
            @RequestBody PhotoEntity photoDetails
    ) {
        PhotoEntity updatedPhoto = photoService.updatePhoto(descriptionId, photoId, photoDetails);
        return ResponseEntity.ok(updatedPhoto);
    }

    /**
     * Delete a specific photo by its ID.
     *
     * @param photoId The ID of the photo to delete.
     * @return A ResponseEntity with no content upon successful deletion.
     */
    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }
}