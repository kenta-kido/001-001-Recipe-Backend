package com.example.backend.repository;

import com.example.backend.entity.IngredientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link IngredientsEntity} persistence.
 * This interface provides database operations for ingredient entities.
 */
@Repository
public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Long> {

    /**
     * Checks if an ingredient with the given name already exists.
     *
     * @param name The name of the ingredient to check.
     * @return {@code true} if an ingredient with the specified name exists, otherwise {@code false}.
     */
    boolean existsByName(String name);
}
