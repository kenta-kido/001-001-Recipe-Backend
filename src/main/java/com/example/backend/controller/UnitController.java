package com.example.backend.controller;

import com.example.backend.entity.UnitEntity;
import com.example.backend.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
public class UnitController {

    @Autowired
    private UnitService unitService;

    // 全てのユニットを取得
    @GetMapping
    public List<UnitEntity> getAllUnits() {
        return unitService.getAllUnits();
    }

    // 特定のユニットを取得
    @GetMapping("/{id}")
    public ResponseEntity<UnitEntity> getUnitById(@PathVariable Long id) {
        return unitService.getUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 新しいユニットを作成
    @PostMapping
    public ResponseEntity<UnitEntity> createUnit(@RequestBody UnitEntity unit) {
        try {
            UnitEntity createdUnit = unitService.createUnit(unit);
            return ResponseEntity.ok(createdUnit);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ユニットを更新
    @PutMapping("/{id}")
    public ResponseEntity<UnitEntity> updateUnit(
            @PathVariable Long id,
            @RequestBody UnitEntity unitDetails
    ) {
        try {
            UnitEntity updatedUnit = unitService.updateUnit(id, unitDetails);
            return ResponseEntity.ok(updatedUnit);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ユニットを削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        try {
            unitService.deleteUnit(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
