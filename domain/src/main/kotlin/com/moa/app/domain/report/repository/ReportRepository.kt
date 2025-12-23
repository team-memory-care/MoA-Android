package com.moa.app.domain.report.repository

import com.moa.app.domain.report.model.DailyReport

interface ReportRepository {
    suspend fun fetchDailyReport(date: String): Result<DailyReport>
}
