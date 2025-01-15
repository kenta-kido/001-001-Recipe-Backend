package com.example.backend.service;

import com.example.backend.entity.UnitEntity;
import com.example.backend.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    // 全てのユニットを取得
    public List<UnitEntity> getAllUnits() {
        return unitRepository.findAll();
    }

    // 特定のユニットを取得
    public Optional<UnitEntity> getUnitById(Long id) {
        return unitRepository.findById(id);
    }

    // 新しいユニットを作成
    public UnitEntity createUnit(UnitEntity unit) {
        if (unitRepository.existsByUnitName(unit.getUnitName())) {
            throw new RuntimeException("Unit with name '" + unit.getUnitName() + "' already exists.");
        }
        return unitRepository.save(unit);
    }

    // ユニットを更新
    public UnitEntity updateUnit(Long id, UnitEntity unitDetails) {
        UnitEntity unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        unit.setUnitName(unitDetails.getUnitName());
        return unitRepository.save(unit);
    }

    // ユニットを削除
    public void deleteUnit(Long id) {
        UnitEntity unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        unitRepository.delete(unit);
    }
}
