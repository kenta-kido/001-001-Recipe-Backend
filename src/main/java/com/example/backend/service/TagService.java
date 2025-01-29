package com.example.backend.service;

import com.example.backend.entity.TagEntity;
import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.TagSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing tags.
 * This service provides CRUD operations for {@link TagEntity} and handles tag synonyms.
 */
@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagSynonymRepository tagSynonymRepository;

    /**
     * Retrieves all tags from the database.
     *
     * @return A list of all {@link TagEntity} objects.
     */
    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    /**
     * Retrieves a tag by its ID.
     *
     * @param id The ID of the tag.
     * @return An {@link Optional} containing the {@link TagEntity} if found.
     */
    public Optional<TagEntity> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    /**
     * Retrieves a tag by its name.
     *
     * @param name The name of the tag.
     * @return An {@link Optional} containing the {@link TagEntity} if found.
     */
    public Optional<TagEntity> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * Creates a new tag and automatically registers its default synonym.
     *
     * @param tag The tag entity to create.
     * @return The saved {@link TagEntity}.
     * @throws RuntimeException if the tag name already exists.
     */
    public TagEntity createTag(TagEntity tag) {
        // Check if the tag already exists
        if (tagRepository.existsByName(tag.getName())) {
            throw new RuntimeException("Tag already exists");
        }

        // Save the tag
        TagEntity savedTag = tagRepository.save(tag);

        // Register the default synonym (same as the tag name)
        TagSynonymEntity synonym = new TagSynonymEntity();
        synonym.setTag(savedTag);
        synonym.setSynonym(tag.getName()); // Use the tag name as the default synonym

        tagSynonymRepository.save(synonym);
        return savedTag;
    }

    /**
     * Updates an existing tag.
     *
     * @param id The ID of the tag to update.
     * @param tagDetails The updated tag details.
     * @return The updated {@link TagEntity}.
     * @throws RuntimeException if the tag is not found.
     */
    public TagEntity updateTag(Long id, TagEntity tagDetails) {
        TagEntity existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        existingTag.setName(tagDetails.getName());
        existingTag.setCategory(tagDetails.getCategory());
        return tagRepository.save(existingTag);
    }

    /**
     * Deletes a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     * @throws RuntimeException if the tag is not found.
     */
    public void deleteTag(Long id) {
        TagEntity existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tagRepository.delete(existingTag);
    }

    /**
     * Checks if a tag with a given name and category exists.
     *
     * @param name The name of the tag.
     * @param category The category of the tag.
     * @return true if the tag exists, false otherwise.
     */
    public boolean existsByNameAndCategory(String name, String category) {
        return tagRepository.existsByNameAndCategory(name, category);
    }
}
