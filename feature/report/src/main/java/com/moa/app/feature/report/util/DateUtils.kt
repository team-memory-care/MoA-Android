package com.moa.app.feature.report.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

object DateUtils {
    private val MONTH_DAY_FORMATTER = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREA)
    private val YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 M월", Locale.KOREA)
    private val WEEK_FIELDS_KOREA = WeekFields.of(Locale.KOREA)

    fun formatToMonthDay(date: LocalDate = LocalDate.now()): String {
        return date.format(MONTH_DAY_FORMATTER)
    }

    fun formatToMonthWeek(date: LocalDate = LocalDate.now()): String {
        val weekOfMonth = date.get(WEEK_FIELDS_KOREA.weekOfMonth())
        val month = date.monthValue

        return "${month}월 ${weekOfMonth.coerceAtLeast(1)}주차"
    }

    fun formatToYearMonth(date: LocalDate = LocalDate.now()): String {
        return date.format(YEAR_MONTH_FORMATTER)
    }
}
