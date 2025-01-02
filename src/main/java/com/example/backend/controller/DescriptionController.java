package com.example.backend.controller;

import com.example.backend.entity.DescriptionEntity;
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

    @PostMapping
    public ResponseEntity<DescriptionEntity> createDescription(
            @PathVariable Long recipeId,
            @RequestBody DescriptionEntity description
    ) {
        DescriptionEntity createdDescription = descriptionService.createDescription(recipeId, description);
        return ResponseEntity.ok(createdDescription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DescriptionEntity> updateDescription(
            @PathVariable Long id,
            @RequestBody DescriptionEntity descriptionDetails
    ) {
        DescriptionEntity updatedDescription = descriptionService.updateDescription(id, descriptionDetails);
        return ResponseEntity.ok(updatedDescription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescription(@PathVariable Long id) {
        descriptionService.deleteDescription(id);
        return ResponseEntity.noContent().build();
    }
}
