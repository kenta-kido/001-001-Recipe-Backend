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

    @GetMapping("/synonyms/search")
    public List<TagSynonymEntity> searchSynonyms(@RequestParam String keyword) {
        return tagSynonymService.searchBySynonym(keyword);
    }
    @GetMapping("/{tagId}/synonyms")
    public ResponseEntity<List<TagSynonymEntity>> getSynonymsByTag(@PathVariable Long tagId) {
        List<TagSynonymEntity> synonyms = tagSynonymService.getSynonymsByTagId(tagId);
        return ResponseEntity.ok(synonyms);
    }
    @PostMapping("/synonyms")
    public ResponseEntity<TagSynonymEntity> createSynonym(@RequestBody TagSynonymEntity synonym) {
        return ResponseEntity.ok(tagSynonymService.createSynonym(synonym));
    }

    @PostMapping("/{tagId}/synonyms")
    public ResponseEntity<TagSynonymEntity> addSynonymToTag(
            @PathVariable Long tagId,
            @RequestBody TagSynonymRequestDTO synonymRequest
    ) {
        TagSynonymEntity synonym = tagSynonymService.addSynonymToTag(tagId, synonymRequest.getSynonym());
        return ResponseEntity.ok(synonym);
    }
    
    @DeleteMapping("/synonyms/{id}")
    public ResponseEntity<Void> deleteSynonym(@PathVariable Long id) {
        tagSynonymService.deleteSynonym(id);
        return ResponseEntity.noContent().build();
    }
}
