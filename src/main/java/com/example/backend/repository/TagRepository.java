package com.example.backend.repository;

import com.example.backend.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing {@link TagEntity} persistence.
 * This interface provides database operations for handling tag entities.
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    /**
     * Retrieves a tag entity by its name.
     *
     * @param name The name of the tag to retrieve.
     * @return An {@link Optional} containing the {@link TagEntity} if found, otherwise empty.
     */
    Optional<TagEntity> findByName(String name);

    /**
     * Checks if a tag with the given name already exists.
     *
     * @param name The name of the tag to check.
     * @return {@code true} if a tag with the specified name exists, otherwise {@code false}.
     */
    boolean existsByName(String name);

    /**
     * Checks if a tag with the given name and category already exists.
     *
     * @param name     The name of the tag to check.
     * @param category The category of the tag to check.
     * @return {@code true} if a tag with the specified name and category exists, otherwise {@code false}.
     */
    boolean existsByNameAndCategory(String name, String category);
}
