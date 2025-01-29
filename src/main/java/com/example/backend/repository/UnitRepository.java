package com.example.backend.repository;

import com.example.backend.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link UnitEntity} persistence.
 * This interface provides database operations for handling measurement units.
 */
@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    /**
     * Checks if a unit with the given name already exists.
     *
     * @param unitName The name of the unit to check.
     * @return {@code true} if a unit with the specified name exists, otherwise {@code false}.
     */
    boolean existsByUnitName(String unitName);
}
