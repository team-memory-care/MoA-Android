package com.moa.app.domain.report.usecase

import com.moa.app.domain.report.model.WeeklyReport
import com.moa.app.domain.report.repository.ReportRepository
import javax.inject.Inject

class FetchWeeklyReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {
    suspend operator fun invoke(year: Int, month: Int, week: Int, parentId: Long? = null): Result<WeeklyReport?> {
        return reportRepository.fetchWeeklyReport(year, month, week, parentId)
    }
}
