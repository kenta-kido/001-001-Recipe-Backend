package com.example.backend.service;

import com.example.backend.entity.TagEntity;
import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.TagSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for managing tag synonyms.
 * This service provides CRUD operations for {@link TagSynonymEntity}.
 */
@Service
public class TagSynonymService {

    @Autowired
    private TagSynonymRepository tagSynonymRepository;

    @Autowired
    private TagRepository tagRepository;

    /**
     * Retrieves all synonyms associated with a specific tag.
     *
     * @param tagId The ID of the tag.
     * @return A list of {@link TagSynonymEntity} associated with the given tag.
     */
    public List<TagSynonymEntity> getSynonymsByTagId(Long tagId) {
        return tagSynonymRepository.findByTagTagId(tagId);
    }
    
    /**
     * Searches for tag synonyms that contain the given keyword (case-insensitive).
     *
     * @param synonym The keyword to search for.
     * @return A list of {@link TagSynonymEntity} containing the given keyword.
     */
    public List<TagSynonymEntity> searchBySynonym(String synonym) {
        return tagSynonymRepository.findBySynonymContainingIgnoreCase(synonym);
    }

    /**
     * Adds a synonym to an existing tag.
     *
     * @param tagId The ID of the tag.
     * @param synonymName The synonym to add.
     * @return The saved {@link TagSynonymEntity}.
     * @throws RuntimeException if the tag is not found.
     */
    public TagSynonymEntity addSynonymToTag(Long tagId, String synonymName) {
        // Retrieve the tag by ID
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // Create a new synonym entity
        TagSynonymEntity synonym = new TagSynonymEntity();
        synonym.setTag(tag);
        synonym.setSynonym(synonymName);

        // Save the synonym
        return tagSynonymRepository.save(synonym);
    }

    /**
     * Creates a new tag synonym.
     *
     * @param synonym The synonym entity to create.
     * @return The saved {@link TagSynonymEntity}.
     */
    public TagSynonymEntity createSynonym(TagSynonymEntity synonym) {
        return tagSynonymRepository.save(synonym);
    }

    /**
     * Deletes a synonym by its ID.
     *
     * @param id The ID of the synonym to delete.
     */
    public void deleteSynonym(Long id) {
        tagSynonymRepository.deleteById(id);
    }
}
