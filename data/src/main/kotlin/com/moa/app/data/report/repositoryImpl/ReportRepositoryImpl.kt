package com.moa.app.data.report.repositoryImpl

import com.moa.app.data.report.datasource.ReportDataSource
import com.moa.app.data.report.model.response.toDomain
import com.moa.app.domain.report.model.DailyReport
import com.moa.app.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource
) : ReportRepository {

    override suspend fun fetchDailyReport(date: String): Result<DailyReport> {
        return reportDataSource.fetchDailyReport(date)
            .mapCatching { it.toDomain() }
    }
}
