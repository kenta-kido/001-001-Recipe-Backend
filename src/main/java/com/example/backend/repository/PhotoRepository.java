package com.example.backend.repository;

import com.example.backend.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link PhotoEntity} persistence.
 * This interface provides database operations for photo storage and retrieval.
 */
@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
    
    // Additional query methods can be added here for advanced photo retrieval.
}
