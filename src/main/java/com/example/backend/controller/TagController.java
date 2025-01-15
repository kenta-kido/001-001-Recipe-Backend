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

    // 全てのタグを取得
    @GetMapping
    public List<TagEntity> getAllTags() {
        return tagService.getAllTags();
    }

    // IDでタグを取得
    @GetMapping("/{id}")
    public ResponseEntity<TagEntity> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // タグを作成
    @PostMapping
    public ResponseEntity<TagEntity> createTag(@RequestBody TagEntity tag) {
        try {
            TagEntity createdTag = tagService.createTag(tag);
            return ResponseEntity.ok(createdTag);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // タグを更新
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

    // タグを削除
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
