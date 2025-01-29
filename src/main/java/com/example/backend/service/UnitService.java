package com.example.backend.service;

import com.example.backend.entity.UnitEntity;
import com.example.backend.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing units.
 * This service provides CRUD operations for {@link UnitEntity}.
 */
@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    /**
     * Retrieves all units from the database.
     *
     * @return A list of all {@link UnitEntity} objects.
     */
    public List<UnitEntity> getAllUnits() {
        return unitRepository.findAll();
    }

    /**
     * Retrieves a unit by its ID.
     *
     * @param id The ID of the unit.
     * @return An {@link Optional} containing the {@link UnitEntity} if found.
     */
    public Optional<UnitEntity> getUnitById(Long id) {
        return unitRepository.findById(id);
    }

    /**
     * Creates a new unit if it does not already exist.
     *
     * @param unit The unit entity to create.
     * @return The saved {@link UnitEntity}.
     * @throws RuntimeException if a unit with the same name already exists.
     */
    public UnitEntity createUnit(UnitEntity unit) {
        if (unitRepository.existsByUnitName(unit.getUnitName())) {
            throw new RuntimeException("Unit with name '" + unit.getUnitName() + "' already exists.");
        }
        return unitRepository.save(unit);
    }

    /**
     * Updates an existing unit.
     *
     * @param id The ID of the unit to update.
     * @param unitDetails The updated unit details.
     * @return The updated {@link UnitEntity}.
     * @throws RuntimeException if the unit is not found.
     */
    public UnitEntity updateUnit(Long id, UnitEntity unitDetails) {
        UnitEntity unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        unit.setUnitName(unitDetails.getUnitName());
        return unitRepository.save(unit);
    }

    /**
     * Deletes a unit by its ID.
     *
     * @param id The ID of the unit to delete.
     * @throws RuntimeException if the unit is not found.
     */
    public void deleteUnit(Long id) {
        UnitEntity unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        unitRepository.delete(unit);
    }

    /**
     * Checks if a unit with a given name exists.
     *
     * @param name The name of the unit.
     * @return true if the unit exists, false otherwise.
     */
    public boolean existsByName(String name) {
        return unitRepository.existsByUnitName(name);
    }
}
