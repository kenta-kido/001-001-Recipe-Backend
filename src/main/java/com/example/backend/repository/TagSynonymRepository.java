package com.example.backend.repository;

import com.example.backend.entity.TagSynonymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link TagSynonymEntity} persistence.
 * This interface provides database operations for handling tag synonyms.
 */
@Repository
public interface TagSynonymRepository extends JpaRepository<TagSynonymEntity, Long> {

    /**
     * Retrieves a list of tag synonyms that contain the given keyword (case insensitive).
     *
     * @param synonym The keyword to search for in tag synonyms.
     * @return A list of {@link TagSynonymEntity} objects matching the keyword.
     */
    List<TagSynonymEntity> findBySynonymContainingIgnoreCase(String synonym);

    /**
     * Retrieves a list of synonyms associated with a specific tag.
     *
     * @param tagId The ID of the tag whose synonyms are to be retrieved.
     * @return A list of {@link TagSynonymEntity} objects linked to the given tag.
     */
    List<TagSynonymEntity> findByTagTagId(Long tagId);
}
