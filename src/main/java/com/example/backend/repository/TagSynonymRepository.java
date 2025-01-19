package com.example.backend.repository;

import com.example.backend.entity.TagSynonymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagSynonymRepository extends JpaRepository<TagSynonymEntity, Long> {
    List<TagSynonymEntity> findBySynonymContainingIgnoreCase(String synonym);
}
