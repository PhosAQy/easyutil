package com.phosa;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类，用于处理常见的日期操作。
 * <p>提供了一些常用的日期处理方法。
 */
public class DateUtil {

    /**
     * 获取当前日期。
     *
     * @return 当前日期的LocalDate对象
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前日期和时间。
     *
     * @return 当前日期和时间的LocalDateTime对象
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 将字符串解析为LocalDate对象。
     *
     * @param dateString 日期字符串，格式为yyyy-MM-dd
     * @return 解析后的LocalDate对象
     */
    public static LocalDate parseDate(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * 将LocalDate格式化为字符串。
     *
     * @param date 要格式化的LocalDate对象
     * @param pattern 格式化的模式，例如"yyyy-MM-dd"
     * @return 格式化后的日期字符串
     */
    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
    /**
     * 将LocalDateTime格式化为字符串。
     *
     * @param dateTime 要格式化的LocalDateTime对象
     * @param pattern 格式化的模式，例如"yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }
    /**
     * 计算两个日期之间的天数差。
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 两个日期之间的天数差
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 将java.util.Date转换为LocalDate。
     *
     * @param date 要转换的java.util.Date对象
     * @return 转换后的LocalDate对象
     */
    public static LocalDate convertDateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将LocalDate转换为java.util.Date。
     *
     * @param localDate 要转换的LocalDate对象
     * @return 转换后的java.util.Date对象
     */
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 检查给定的年份是否是闰年。
     *
     * @param year 要检查的年份
     * @return 如果是闰年，返回true，否则返回false
     */
    public static boolean isLeapYear(int year) {
        return LocalDate.of(year, 1, 1).isLeapYear();
    }

    /**
     * 将字符串解析为LocalDateTime对象。
     *
     * @param dateTimeString 日期时间字符串，格式为yyyy-MM-dd HH:mm:ss
     * @return 解析后的LocalDateTime对象
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    /**
     * 获取指定月份的天数。
     *
     * @param year 年份
     * @param month 月份（1-12）
     * @return 指定月份的天数
     */
    public static int getDaysInMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    /**
     * 获取指定日期是星期几。
     *
     * @param date 指定的日期
     * @return 星期几（1表示星期一，7表示星期日）
     */
    public static int getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    /**
     * 获取当前日期是本年度的第几天。
     *
     * @return 当前日期是本年度的第几天
     */
    public static int getDayOfYear() {
        return LocalDate.now().getDayOfYear();
    }
    /**
     * 将java.util.Calendar转换为LocalDate。
     *
     * @param calendar 要转换的Calendar对象
     * @return 转换后的LocalDate对象
     */
    public static LocalDate convertCalendarToLocalDate(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("输入的Calendar对象不能为空");
        }
        return Instant.ofEpochMilli(calendar.getTimeInMillis()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 获取当前时间的Calendar实例。
     *
     * @return 当前时间的Calendar对象
     */
    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

}
