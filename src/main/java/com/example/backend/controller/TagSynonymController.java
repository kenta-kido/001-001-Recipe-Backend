package com.example.backend.controller;

import com.example.backend.dto.TagSynonymRequestDTO;
import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.service.TagSynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagSynonymController {

    @Autowired
    private TagSynonymService tagSynonymService;

    /**
     * Search for tag synonyms by a keyword.
     *
     * @param keyword The keyword to search for synonyms.
     * @return A list of TagSynonymEntity objects matching the search keyword.
     */
    @GetMapping("/synonyms/search")
    public List<TagSynonymEntity> searchSynonyms(@RequestParam String keyword) {
        return tagSynonymService.searchBySynonym(keyword);
    }

    /**
     * Retrieve all synonyms associated with a specific tag.
     *
     * @param tagId The ID of the tag whose synonyms are to be retrieved.
     * @return A ResponseEntity containing a list of TagSynonymEntity objects.
     */
    @GetMapping("/{tagId}/synonyms")
    public ResponseEntity<List<TagSynonymEntity>> getSynonymsByTag(@PathVariable Long tagId) {
        List<TagSynonymEntity> synonyms = tagSynonymService.getSynonymsByTagId(tagId);
        return ResponseEntity.ok(synonyms);
    }

    /**
     * Create a new synonym.
     *
     * @param synonym The TagSynonymEntity object containing the details of the synonym to create.
     * @return A ResponseEntity containing the created TagSynonymEntity.
     */
    @PostMapping("/synonyms")
    public ResponseEntity<TagSynonymEntity> createSynonym(@RequestBody TagSynonymEntity synonym) {
        return ResponseEntity.ok(tagSynonymService.createSynonym(synonym));
    }

    /**
     * Add a synonym to a specific tag.
     *
     * @param tagId           The ID of the tag to which the synonym will be added.
     * @param synonymRequest  A DTO containing the synonym string to add.
     * @return A ResponseEntity containing the created TagSynonymEntity.
     */
    @PostMapping("/{tagId}/synonyms")
    public ResponseEntity<TagSynonymEntity> addSynonymToTag(
            @PathVariable Long tagId,
            @RequestBody TagSynonymRequestDTO synonymRequest
    ) {
        TagSynonymEntity synonym = tagSynonymService.addSynonymToTag(tagId, synonymRequest.getSynonym());
        return ResponseEntity.ok(synonym);
    }

    /**
     * Delete a synonym by its ID.
     *
     * @param id The ID of the synonym to delete.
     * @return A ResponseEntity with no content upon successful deletion.
     */
    @DeleteMapping("/synonyms/{id}")
    public ResponseEntity<Void> deleteSynonym(@PathVariable Long id) {
        tagSynonymService.deleteSynonym(id);
        return ResponseEntity.noContent().build();
    }
}