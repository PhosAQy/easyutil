package com.phosa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Slf4j
public class CsvUtil {
    public static List<CSVRecord> getCsvContent(String filename) {
        return getCsvContent(filename, CSVFormat.DEFAULT);

    }
    public static List<CSVRecord> getCsvContent(String filename, CSVFormat format) {
        try (Reader reader = new FileReader(filename);
             CSVParser csvParser = new CSVParser(reader, format)) {
            return csvParser.getRecords();
        } catch (IOException e) {
            log.error("Error loading accounts from CSV: " + e.getMessage(), e);
            throw new IllegalStateException("Cannot load accounts", e);
        }

    }
}
