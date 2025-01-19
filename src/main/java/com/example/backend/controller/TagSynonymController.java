package com.example.backend.controller;

import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.service.TagSynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags/synonyms")
public class TagSynonymController {

    @Autowired
    private TagSynonymService tagSynonymService;

    @GetMapping("/search")
    public List<TagSynonymEntity> searchSynonyms(@RequestParam String keyword) {
        return tagSynonymService.searchBySynonym(keyword);
    }

    @PostMapping
    public ResponseEntity<TagSynonymEntity> createSynonym(@RequestBody TagSynonymEntity synonym) {
        return ResponseEntity.ok(tagSynonymService.createSynonym(synonym));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSynonym(@PathVariable Long id) {
        tagSynonymService.deleteSynonym(id);
        return ResponseEntity.noContent().build();
    }
}
