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

    /**
     * Fetch all units.
     *
     * @return A list of all UnitEntity objects.
     */
    @GetMapping
    public List<UnitEntity> getAllUnits() {
        return unitService.getAllUnits();
    }

    /**
     * Fetch a specific unit by its ID.
     *
     * @param id The ID of the unit to retrieve.
     * @return A ResponseEntity containing the UnitEntity if found, or a 404 response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UnitEntity> getUnitById(@PathVariable Long id) {
        return unitService.getUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new unit.
     *
     * @param unit The UnitEntity object containing the details of the unit to create.
     * @return A ResponseEntity containing the created UnitEntity, or a 400 response if creation fails.
     */
    @PostMapping
    public ResponseEntity<UnitEntity> createUnit(@RequestBody UnitEntity unit) {
        try {
            UnitEntity createdUnit = unitService.createUnit(unit);
            return ResponseEntity.ok(createdUnit);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Update an existing unit by its ID.
     *
     * @param id          The ID of the unit to update.
     * @param unitDetails The updated UnitEntity object containing new unit details.
     * @return A ResponseEntity containing the updated UnitEntity, or a 404 response if the unit is not found.
     */
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

    /**
     * Delete a unit by its ID.
     *
     * @param id The ID of the unit to delete.
     * @return A ResponseEntity with no content upon successful deletion, or a 404 response if the unit is not found.
     */
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