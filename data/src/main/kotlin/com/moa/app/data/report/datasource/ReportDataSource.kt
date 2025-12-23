package com.moa.app.data.report.datasource

import com.moa.app.data.report.model.response.DailyReportResponse

interface ReportDataSource {
    suspend fun fetchDailyReport(date: String): Result<DailyReportResponse>
}
