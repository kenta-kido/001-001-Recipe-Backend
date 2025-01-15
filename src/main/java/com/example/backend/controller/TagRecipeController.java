package com.example.backend.controller;

import com.example.backend.entity.TagRecipeEntity;
import com.example.backend.service.TagRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes/{recipeId}/tags")
public class TagRecipeController {

    @Autowired
    private TagRecipeService tagRecipeService;

    // 特定のレシピに関連付けられたタグを取得
    @GetMapping
    public List<TagRecipeEntity> getTagsByRecipeId(@PathVariable Long recipeId) {
        return tagRecipeService.getTagsByRecipeId(recipeId);
    }

    // 特定のタグに関連付けられたレシピを取得
    @GetMapping("/tag/{tagId}")
    public List<TagRecipeEntity> getRecipesByTagId(@PathVariable Long tagId) {
        return tagRecipeService.getRecipesByTagId(tagId);
    }

    // レシピにタグを追加
    @PostMapping("/{tagId}")
    public ResponseEntity<TagRecipeEntity> addTagToRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long tagId
    ) {
        TagRecipeEntity tagRecipe = tagRecipeService.addTagToRecipe(recipeId, tagId);
        return ResponseEntity.ok(tagRecipe);
    }

    // レシピからタグを削除
    @DeleteMapping("/{tagRecipeId}")
    public ResponseEntity<Void> removeTagFromRecipe(@PathVariable Long tagRecipeId) {
        tagRecipeService.removeTagFromRecipe(tagRecipeId);
        return ResponseEntity.noContent().build();
    }
}
