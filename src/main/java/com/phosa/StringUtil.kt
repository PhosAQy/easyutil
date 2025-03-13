package com.phosa

import java.util.*
import java.util.Map
import java.util.function.IntFunction
import java.util.function.Supplier
import java.util.stream.Collectors

/**
 * 字符串工具类，用于对字符串进行常见操作。
 *
 * 提供了一些常用的字符串处理方法。
 */
object StringUtil {
    /**
     * 查找字符串中出现次数最多的字符。
     *
     * @param str 待检查的字符串
     * @return 出现次数最多的字符
     */
    fun findMostFrequentChar(str: String): Char {
        require(str.isNotEmpty()) { "输入字符串不能为空" }
        val frequencyMap: MutableMap<Char?, Int?> = HashMap<Char?, Int?>()
        for (c in str.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0)!! + 1)
        }
        return frequencyMap.entries.stream()
            .max(Map.Entry.comparingByValue<Char?, Int?>())
            .orElseThrow<IllegalArgumentException?>(Supplier { java.lang.IllegalArgumentException("无法找到最多的字符") })
            .key!!
    }

    /**
     * 将字符串中的元音字母移除。
     *
     * @param str 待处理的字符串
     * @return 移除元音字母后的字符串
     */
    fun removeVowels(str: String?): String? {
        if (str == null) {
            return null
        }
        return str.replace("[aeiouAEIOU]".toRegex(), "")
    }

    /**
     * 检查字符串是否为回文。
     *
     * @param str 待检查的字符串
     * @return 如果字符串是回文，返回true，否则返回false
     */
    fun isPalindrome(str: String?): Boolean {
        if (str == null) {
            return false
        }
        val cleanedStr = str.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase(Locale.getDefault())
        return cleanedStr.contentEquals(StringBuilder(cleanedStr).reverse())
    }

    /**
     * 统计字符串中每个单词的出现次数。
     *
     * @param str 待统计的字符串
     * @return 包含每个单词出现次数的映射
     */
    fun wordFrequency(str: String?): MutableMap<String?, Int?> {
        if (str == null || str.isEmpty()) {
            return HashMap<String?, Int?>()
        }
        val words: Array<String?> =
            str.lowercase(Locale.getDefault()).split("\\W+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val frequencyMap: MutableMap<String?, Int?> = HashMap<String?, Int?>()
        for (word in words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0)!! + 1)
        }
        return frequencyMap
    }

    /**
     * 提取字符串中的所有数字。
     *
     * @param str 待提取的字符串
     * @return 包含所有数字的字符串
     */
    fun extractDigits(str: String?): String {
        if (str == null) {
            return ""
        }
        return str.replace("[^0-9]".toRegex(), "")
    }

    /**
     * 将字符串中的重复字符移除，只保留第一次出现的字符。
     *
     * @param str 待处理的字符串
     * @return 去除重复字符后的字符串
     */
    fun removeDuplicateChars(str: String?): String? {
        if (str == null) {
            return null
        }
        return str.chars()
            .distinct()
            .mapToObj<String?>(IntFunction { c: Int -> c.toChar().toString() })
            .collect(Collectors.joining())
    }

    /**
     * 将字符串中的所有字母转换为反转大小写。
     *
     * @param str 待处理的字符串
     * @return 反转大小写后的字符串
     */
    fun reverseCase(str: String?): String? {
        if (str == null) {
            return null
        }
        val result = StringBuilder()
        for (c in str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(c.lowercaseChar())
            } else if (Character.isLowerCase(c)) {
                result.append(c.uppercaseChar())
            } else {
                result.append(c)
            }
        }
        return result.toString()
    }

    /**
     * 计算字符串中单词的数量。
     *
     * @param str 待计算的字符串
     * @return 字符串中的单词数量
     */
    fun countWords(str: String?): Int {
        if (str == null || str.trim { it <= ' ' }.isEmpty()) {
            return 0
        }
        val words: Array<String?> =
            str.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return words.size
    }

    /**
     * 将字符串的字符按字典顺序排序。
     *
     * @param str 待排序的字符串
     * @return 按字典顺序排序后的字符串
     */
    fun sortCharacters(str: String?): String? {
        if (str == null) {
            return null
        }
        return str.chars()
            .sorted()
            .mapToObj<String?>(IntFunction { c: Int -> c.toChar().toString() })
            .collect(Collectors.joining())
    }

    /**
     * 将字符串中的连续空格压缩为单个空格。
     *
     * @param str 待处理的字符串
     * @return 压缩后的字符串
     */
    fun compressSpaces(str: String?): String? {
        if (str == null) {
            return null
        }
        return str.replace("\\s+".toRegex(), " ").trim { it <= ' ' }
    }
}
