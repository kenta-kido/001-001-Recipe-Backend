package com.example.backend.repository;

import com.example.backend.entity.IngredientsSynonymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsSynonymRepository extends JpaRepository<IngredientsSynonymEntity, Long> {
    List<IngredientsSynonymEntity> findBySynonymContainingIgnoreCase(String synonym);
}
