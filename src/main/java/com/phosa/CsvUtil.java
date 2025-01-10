package com.phosa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Csv 工具
 */
@Slf4j
public class CsvUtil {

    /**
     * 获取Csv文件的内容
     *
     * @param filename 文件路径及名称
     * @return 文件内容
     */
    public static List<CSVRecord> getCsvContent(String filename) {
        return getCsvContent(filename, CSVFormat.DEFAULT);

    }

    /**
     * 获取Csv文件的内容
     *
     * @param filename 文件路径及名称
     * @param format 文件格式
     * @return 文件内容
     */
    public static List<CSVRecord> getCsvContent(String filename, CSVFormat format) {
        try (Reader reader = new FileReader(filename);
             CSVParser csvParser = new CSVParser(reader, format)) {
            return csvParser.getRecords();
        } catch (IOException e) {
            log.error("Error loading accounts from CSV: {}", e.getMessage(), e);
            throw new IllegalStateException("Cannot load accounts", e);
        }

    }
}
