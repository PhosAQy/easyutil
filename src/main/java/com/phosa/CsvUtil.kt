package com.phosa

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.FileReader
import java.io.IOException

/**
 * Csv 工具
 */
object CsvUtil {


    val log: Logger = LoggerFactory.getLogger(CsvUtil::class.java)
    /**
     * 获取Csv文件的内容
     *
     * @param filename 文件路径及名称
     * @return 文件内容
     */
    fun getCsvContent(filename: String): MutableList<CSVRecord?>? {
        return getCsvContent(filename, CSVFormat.DEFAULT)
    }

    /**
     * 获取Csv文件的内容
     *
     * @param filename 文件路径及名称
     * @param format 文件格式
     * @return 文件内容
     */
    fun getCsvContent(filename: String, format: CSVFormat): MutableList<CSVRecord?>? {
        try {
            FileReader(filename).use { reader ->
                CSVParser(reader, format).use { csvParser ->
                    return csvParser.records
                }
            }
        } catch (e: IOException) {
            log.error("Error loading accounts from CSV: {}", e.message, e)
            throw IllegalStateException("Cannot load accounts", e)
        }
    }
}
