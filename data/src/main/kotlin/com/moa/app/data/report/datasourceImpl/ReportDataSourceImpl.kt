package com.moa.app.data.report.datasourceImpl

import com.moa.app.data.report.datasource.ReportDataSource
import com.moa.app.data.report.model.response.DailyReportResponse
import com.moa.app.data.report.model.response.MonthlyReportResponse
import com.moa.app.data.report.model.response.WeeklyReportResponse
import com.moa.app.data.report.service.ReportService
import com.moa.app.network.extension.toResult
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService,
) : ReportDataSource {

    override suspend fun fetchDailyReport(date: String, parentId: Long?): Result<DailyReportResponse?> {
        return reportService.fetchDailyReport(date, parentId).toResult { it }
    }

    override suspend fun fetchWeeklyReport(
        year: Int,
        month: Int,
        week: Int,
        parentId: Long?
    ): Result<WeeklyReportResponse?> {
        return reportService.fetchWeeklyReport(year, month, week, parentId).toResult { it }
    }

    override suspend fun fetchMonthlyReport(year: Int, month: Int, parentId: Long?): Result<MonthlyReportResponse?> {
        return reportService.fetchMonthlyReport(year, month, parentId).toResult { it }
    }

}
