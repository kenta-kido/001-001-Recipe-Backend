package com.example.backend.controller;

import com.example.backend.entity.DescriptionEntity;
import com.example.backend.entity.PhotoEntity;
import com.example.backend.service.DescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/recipes/{recipeId}/descriptions")
public class DescriptionController {

    @Autowired
    private DescriptionService descriptionService;

    /**
     * Fetch all descriptions associated with a specific recipe.
     *
     * @param recipeId The ID of the recipe.
     * @return A list of DescriptionEntity objects for the given recipe.
     */
    @GetMapping
    public List<DescriptionEntity> getDescriptionsByRecipeId(@PathVariable Long recipeId) {
        return descriptionService.getDescriptionsByRecipeId(recipeId);
    }

    /**
     * Fetch the photo associated with a specific description by its ID.
     *
     * @param id The ID of the description.
     * @return A base64-encoded string of the photo if it exists, or a 404 response.
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<String> getPhotoByDescriptionId(@PathVariable Long id) {
        return descriptionService.getPhotoByDescriptionId(id)
                .map(photo -> ResponseEntity.ok("data:image/jpeg;base64," + photo.getBinaryPhoto()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new description associated with a specific recipe.
     *
     * @param recipeId    The ID of the recipe to associate the description with.
     * @param description The description entity containing details and optionally a photo.
     * @return The created DescriptionEntity, or a 400 response if creation fails.
     */
    @PostMapping
    public ResponseEntity<DescriptionEntity> createDescription(
            @PathVariable Long recipeId,
            @RequestBody DescriptionEntity description
    ) {
        try {
            DescriptionEntity createdDescription = descriptionService.createDescriptionWithPhoto(recipeId, description);
            return ResponseEntity.ok(createdDescription);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    /**
     * Update an existing description by its ID.
     *
     * @param id                The ID of the description to update.
     * @param descriptionDetails The updated details of the description.
     * @return The updated DescriptionEntity object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DescriptionEntity> updateDescription(
            @PathVariable Long id,
            @RequestBody DescriptionEntity descriptionDetails
    ) {
        DescriptionEntity updatedDescription = descriptionService.updateDescription(id, descriptionDetails);
        return ResponseEntity.ok(updatedDescription);
    }

    /**
     * Update the photo associated with a specific description by its ID.
     *
     * @param id      The ID of the description to update the photo for.
     * @param newPhoto The new photo entity to associate with the description.
     * @return The updated DescriptionEntity object.
     */
    @PutMapping("/{id}/photo")
    public ResponseEntity<DescriptionEntity> updateDescriptionPhoto(
            @PathVariable Long id,
            @RequestBody PhotoEntity newPhoto
    ) {
        DescriptionEntity updatedDescription = descriptionService.updateDescriptionPhoto(id, newPhoto);
        return ResponseEntity.ok(updatedDescription);
    }

    /**
     * Delete a description by its ID.
     *
     * @param id The ID of the description to delete.
     * @return A 204 No Content response on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescription(@PathVariable Long id) {
        descriptionService.deleteDescription(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete the photo associated with a specific description by its ID.
     *
     * @param id The ID of the description whose photo is to be deleted.
     * @return A 204 No Content response on successful deletion.
     */
    @DeleteMapping("/{id}/photo")
    public ResponseEntity<Void> deleteDescriptionPhoto(@PathVariable Long id) {
        descriptionService.deleteDescriptionPhoto(id);
        return ResponseEntity.noContent().build();
    }
}
