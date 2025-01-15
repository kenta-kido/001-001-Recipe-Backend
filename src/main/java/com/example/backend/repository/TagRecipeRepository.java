package com.example.backend.repository;

import com.example.backend.entity.TagRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRecipeRepository extends JpaRepository<TagRecipeEntity, Long> {
    List<TagRecipeEntity> findByRecipeRecipeId(Long recipeId);
    List<TagRecipeEntity> findByTagTagId(Long tagId);
}
