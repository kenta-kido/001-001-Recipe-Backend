package com.example.backend.repository;

import com.example.backend.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {
    boolean existsByUnitName(String unitName); // ユニット名が存在するか確認
}
