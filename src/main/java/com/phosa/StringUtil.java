package com.phosa;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串工具类，用于对字符串进行常见操作。
 * <p>提供了一些常用的字符串处理方法。
 * @since 1.0.2
 */
public class StringUtil {

    /**
     * 查找字符串中出现次数最多的字符。
     *
     * @param str 待检查的字符串
     * @return 出现次数最多的字符
     */
    public static char findMostFrequentChar(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("输入字符串不能为空");
        }
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : str.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalArgumentException("无法找到最多的字符"))
                .getKey();
    }

    /**
     * 将字符串中的元音字母移除。
     *
     * @param str 待处理的字符串
     * @return 移除元音字母后的字符串
     */
    public static String removeVowels(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("[aeiouAEIOU]", "");
    }

    /**
     * 检查字符串是否为回文。
     *
     * @param str 待检查的字符串
     * @return 如果字符串是回文，返回true，否则返回false
     */
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        String cleanedStr = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleanedStr.contentEquals(new StringBuilder(cleanedStr).reverse());
    }

    /**
     * 统计字符串中每个单词的出现次数。
     *
     * @param str 待统计的字符串
     * @return 包含每个单词出现次数的映射
     */
    public static Map<String, Integer> wordFrequency(String str) {
        if (str == null || str.isEmpty()) {
            return new HashMap<>();
        }
        String[] words = str.toLowerCase().split("\\W+");
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }
        return frequencyMap;
    }

    /**
     * 提取字符串中的所有数字。
     *
     * @param str 待提取的字符串
     * @return 包含所有数字的字符串
     */
    public static String extractDigits(String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("[^0-9]", "");
    }

    /**
     * 将字符串中的重复字符移除，只保留第一次出现的字符。
     *
     * @param str 待处理的字符串
     * @return 去除重复字符后的字符串
     */
    public static String removeDuplicateChars(String str) {
        if (str == null) {
            return null;
        }
        return str.chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    /**
     * 将字符串中的所有字母转换为反转大小写。
     *
     * @param str 待处理的字符串
     * @return 反转大小写后的字符串
     */
    public static String reverseCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 计算字符串中单词的数量。
     *
     * @param str 待计算的字符串
     * @return 字符串中的单词数量
     */
    public static int countWords(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }
        String[] words = str.trim().split("\\s+");
        return words.length;
    }

    /**
     * 将字符串的字符按字典顺序排序。
     *
     * @param str 待排序的字符串
     * @return 按字典顺序排序后的字符串
     */
    public static String sortCharacters(String str) {
        if (str == null) {
            return null;
        }
        return str.chars()
                .sorted()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    /**
     * 将字符串中的连续空格压缩为单个空格。
     *
     * @param str 待处理的字符串
     * @return 压缩后的字符串
     */
    public static String compressSpaces(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\s+", " ").trim();
    }
}
