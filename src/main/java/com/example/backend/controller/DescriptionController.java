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

    @GetMapping
    public List<DescriptionEntity> getDescriptionsByRecipeId(@PathVariable Long recipeId) {
        return descriptionService.getDescriptionsByRecipeId(recipeId);
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<String> getPhotoByDescriptionId(@PathVariable Long id) {
        return descriptionService.getPhotoByDescriptionId(id)
                .map(photo -> ResponseEntity.ok("data:image/jpeg;base64," + photo.getBinaryPhoto()))
                .orElse(ResponseEntity.notFound().build());
    }
    
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


    @PutMapping("/{id}")
    public ResponseEntity<DescriptionEntity> updateDescription(
            @PathVariable Long id,
            @RequestBody DescriptionEntity descriptionDetails
    ) {
        DescriptionEntity updatedDescription = descriptionService.updateDescription(id, descriptionDetails);
        return ResponseEntity.ok(updatedDescription);
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity<DescriptionEntity> updateDescriptionPhoto(
            @PathVariable Long id,
            @RequestBody PhotoEntity newPhoto
    ) {
        DescriptionEntity updatedDescription = descriptionService.updateDescriptionPhoto(id, newPhoto);
        return ResponseEntity.ok(updatedDescription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescription(@PathVariable Long id) {
        descriptionService.deleteDescription(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/photo")
    public ResponseEntity<Void> deleteDescriptionPhoto(@PathVariable Long id) {
        descriptionService.deleteDescriptionPhoto(id);
        return ResponseEntity.noContent().build();
    }
}
