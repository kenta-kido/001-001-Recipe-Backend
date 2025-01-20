package com.example.backend.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderUtil {

    public static List<CSVRecord> readCsv(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Path.of(filePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            return new ArrayList<>(csvParser.getRecords());
        }
    }
}