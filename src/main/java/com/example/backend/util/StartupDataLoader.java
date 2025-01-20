package com.example.backend.util;

import com.example.backend.entity.TagEntity;
import com.example.backend.entity.UnitEntity;
import com.example.backend.service.TagService;
import com.example.backend.service.UnitService;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class StartupDataLoader {

    @Autowired
    private TagService tagService;

    @Autowired
    private UnitService unitService;

    @PostConstruct
    public void loadData() {
        loadTagsFromCsv("src/main/resources/csv/tags.csv");
        loadUnitsFromCsv("src/main/resources/csv/units.csv");
    }

    private void loadTagsFromCsv(String filePath) {
        try {
            List<CSVRecord> records = CsvReaderUtil.readCsv(filePath);
            for (CSVRecord record : records) {
                String name = record.get("name");
                String category = record.get("category");

                TagEntity tag = new TagEntity();
                tag.setName(name);
                tag.setCategory(category);

                // 重複チェックして登録
                if (!tagService.existsByNameAndCategory(name, category)) {
                    tagService.createTag(tag);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tags from CSV: " + e.getMessage());
        }
    }

    private void loadUnitsFromCsv(String filePath) {
        try {
            List<CSVRecord> records = CsvReaderUtil.readCsv(filePath);
            for (CSVRecord record : records) {
                String name = record.get("name");

                UnitEntity unit = new UnitEntity();
                unit.setUnitName(name);

                // 重複チェックして登録
                if (!unitService.existsByName(name)) {
                    unitService.createUnit(unit);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading units from CSV: " + e.getMessage());
        }
    }
}
