package com.moa.app.data.report.repositoryImpl

import com.moa.app.data.report.datasource.ReportDataSource
import com.moa.app.data.report.model.response.toDomain
import com.moa.app.domain.report.model.DailyReport
import com.moa.app.domain.report.model.MonthlyReport
import com.moa.app.domain.report.model.WeeklyReport
import com.moa.app.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource,
) : ReportRepository {

    override suspend fun fetchDailyReport(date: String): Result<DailyReport?> {
        return reportDataSource.fetchDailyReport(date)
            .mapCatching { it?.toDomain() }
    }

    override suspend fun fetchWeeklyReport(
        year: Int,
        month: Int,
        week: Int,
    ): Result<WeeklyReport?> {
        return reportDataSource.fetchWeeklyReport(year, month, week)
            .mapCatching { it?.toDomain() }
    }

    override suspend fun fetchMonthlyReport(year: Int, month: Int): Result<MonthlyReport?> {
        return reportDataSource.fetchMonthlyReport(year, month)
            .mapCatching { it?.toDomain() }
    }
}
