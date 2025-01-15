package com.example.backend.service;

import com.example.backend.entity.RecipeEntity;
import com.example.backend.entity.TagEntity;
import com.example.backend.entity.TagRecipeEntity;
import com.example.backend.repository.RecipeRepository;
import com.example.backend.repository.TagRecipeRepository;
import com.example.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagRecipeService {

    @Autowired
    private TagRecipeRepository tagRecipeRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private TagRepository tagRepository;

    // 特定のレシピに関連付けられたタグを取得
    public List<TagRecipeEntity> getTagsByRecipeId(Long recipeId) {
        return tagRecipeRepository.findByRecipeRecipeId(recipeId);
    }

    // 特定のタグに関連付けられたレシピを取得
    public List<TagRecipeEntity> getRecipesByTagId(Long tagId) {
        return tagRecipeRepository.findByTagTagId(tagId);
    }

    // レシピにタグを追加
    public TagRecipeEntity addTagToRecipe(Long recipeId, Long tagId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        TagRecipeEntity tagRecipe = new TagRecipeEntity();
        tagRecipe.setRecipe(recipe);
        tagRecipe.setTag(tag);

        return tagRecipeRepository.save(tagRecipe);
    }

    // レシピからタグを削除
    public void removeTagFromRecipe(Long tagRecipeId) {
        TagRecipeEntity tagRecipe = tagRecipeRepository.findById(tagRecipeId)
                .orElseThrow(() -> new RuntimeException("TagRecipe not found"));

        tagRecipeRepository.delete(tagRecipe);
    }
}
