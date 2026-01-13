package com.moa.app.data.report.datasource

import com.moa.app.data.report.model.response.DailyReportResponse
import com.moa.app.data.report.model.response.MonthlyReportResponse
import com.moa.app.data.report.model.response.WeeklyReportResponse

interface ReportDataSource {
    suspend fun fetchDailyReport(date: String, parentId: Long?): Result<DailyReportResponse?>
    suspend fun fetchWeeklyReport(year: Int, month: Int, week: Int, parentId: Long?): Result<WeeklyReportResponse?>
    suspend fun fetchMonthlyReport(year: Int, month: Int, parentId: Long?): Result<MonthlyReportResponse?>
}
