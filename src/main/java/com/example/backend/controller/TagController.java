package com.example.backend.controller;

import com.example.backend.entity.TagEntity;
import com.example.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * Fetch all tags.
     *
     * @return A list of all TagEntity objects.
     */
    @GetMapping
    public List<TagEntity> getAllTags() {
        return tagService.getAllTags();
    }

    /**
     * Fetch a tag by its ID.
     *
     * @param id The ID of the tag to retrieve.
     * @return A ResponseEntity containing the TagEntity if found, or a 404 response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagEntity> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new tag.
     *
     * @param tag The TagEntity object containing the details of the tag to create.
     * @return A ResponseEntity containing the created TagEntity, or a 400 response if creation fails.
     */
    @PostMapping
    public ResponseEntity<TagEntity> createTag(@RequestBody TagEntity tag) {
        try {
            TagEntity createdTag = tagService.createTag(tag);
            return ResponseEntity.ok(createdTag);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Update an existing tag by its ID.
     *
     * @param id         The ID of the tag to update.
     * @param tagDetails The updated TagEntity object containing new tag details.
     * @return A ResponseEntity containing the updated TagEntity, or a 404 response if the tag is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TagEntity> updateTag(
            @PathVariable Long id,
            @RequestBody TagEntity tagDetails
    ) {
        try {
            TagEntity updatedTag = tagService.updateTag(id, tagDetails);
            return ResponseEntity.ok(updatedTag);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     * @return A ResponseEntity with no content upon successful deletion, or a 404 response if the tag is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        try {
            tagService.deleteTag(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
