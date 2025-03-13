package com.phosa

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * 日期工具类，用于处理常见的日期操作。
 *
 * 提供了一些常用的日期处理方法。
 */
object DateUtil {
    val currentDate: LocalDate
        /**
         * 获取当前日期。
         *
         * @return 当前日期的LocalDate对象
         */
        get() = LocalDate.now()

    val currentDateTime: LocalDateTime
        /**
         * 获取当前日期和时间。
         *
         * @return 当前日期和时间的LocalDateTime对象
         */
        get() = LocalDateTime.now()

    val currentCalendar: Calendar?
        /**
         * 获取当前时间的Calendar实例。
         *
         * @return 当前时间的Calendar对象
         */
        get() = Calendar.getInstance()

    val dayOfYear: Int
        /**
         * 获取当前日期是本年度的第几天。
         *
         * @return 当前日期是本年度的第几天
         */
        get() = LocalDate.now().dayOfYear

    /**
     * 将字符串解析为LocalDate对象。
     *
     * @param dateString 日期字符串，格式为yyyy-MM-dd
     * @return 解析后的LocalDate对象
     */
    fun parseDate(dateString: String, pattern: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDate.parse(dateString, formatter)
    }

    /**
     * 将LocalDate格式化为字符串。
     *
     * @param date 要格式化的LocalDate对象
     * @param pattern 格式化的模式，例如"yyyy-MM-dd"
     * @return 格式化后的日期字符串
     */
    fun formatDate(date: LocalDate, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return date.format(formatter)
    }

    /**
     * 将LocalDateTime格式化为字符串。
     *
     * @param dateTime 要格式化的LocalDateTime对象
     * @param pattern 格式化的模式，例如"yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的日期时间字符串
     */
    fun formatDateTime(dateTime: LocalDateTime, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return dateTime.format(formatter)
    }

    /**
     * 计算两个日期之间的天数差。
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 两个日期之间的天数差
     */
    fun daysBetween(startDate: LocalDate, endDate: LocalDate?): Long {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    /**
     * 将java.util.Date转换为LocalDate。
     *
     * @param date 要转换的java.util.Date对象
     * @return 转换后的LocalDate对象
     */
    fun convertDateToLocalDate(date: Date): LocalDate? {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    /**
     * 将LocalDate转换为java.util.Date。
     *
     * @param localDate 要转换的LocalDate对象
     * @return 转换后的java.util.Date对象
     */
    fun convertLocalDateToDate(localDate: LocalDate): Date {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    /**
     * 检查给定的年份是否是闰年。
     *
     * @param year 要检查的年份
     * @return 如果是闰年，返回true，否则返回false
     */
    fun isLeapYear(year: Int): Boolean {
        return LocalDate.of(year, 1, 1).isLeapYear
    }

    /**
     * 将字符串解析为LocalDateTime对象。
     *
     * @param dateTimeString 日期时间字符串，格式为yyyy-MM-dd HH:mm:ss
     * @return 解析后的LocalDateTime对象
     */
    fun parseDateTime(dateTimeString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.parse(dateTimeString, formatter)
    }

    /**
     * 获取指定月份的天数。
     *
     * @param year 年份
     * @param month 月份（1-12）
     * @return 指定月份的天数
     */
    fun getDaysInMonth(year: Int, month: Int): Int {
        val yearMonth = YearMonth.of(year, month)
        return yearMonth.lengthOfMonth()
    }

    /**
     * 获取指定日期是星期几。
     *
     * @param date 指定的日期
     * @return 星期几（1表示星期一，7表示星期日）
     */
    fun getDayOfWeek(date: LocalDate): Int {
        return date.getDayOfWeek().value
    }


    /**
     * 将java.util.Calendar转换为LocalDate。
     *
     * @param calendar 要转换的Calendar对象
     * @return 转换后的LocalDate对象
     */
    fun convertCalendarToLocalDate(calendar: Calendar): LocalDate? {
        requireNotNull(calendar) { "输入的Calendar对象不能为空" }
        return Instant.ofEpochMilli(calendar.getTimeInMillis()).atZone(ZoneId.systemDefault()).toLocalDate()
    }


}
