package com.moa.app.domain.report.repository

import com.moa.app.domain.report.model.DailyReport
import com.moa.app.domain.report.model.MonthlyReport
import com.moa.app.domain.report.model.WeeklyReport

interface ReportRepository {
    suspend fun fetchDailyReport(date: String): Result<DailyReport?>
    suspend fun fetchWeeklyReport(year: Int, month: Int, week: Int): Result<WeeklyReport?>
    suspend fun fetchMonthlyReport(year: Int, month: Int): Result<MonthlyReport?>
}
