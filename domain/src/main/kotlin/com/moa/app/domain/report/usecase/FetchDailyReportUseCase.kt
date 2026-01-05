package com.moa.app.domain.report.usecase

import com.moa.app.domain.report.model.DailyReport
import com.moa.app.domain.report.repository.ReportRepository
import javax.inject.Inject

class FetchDailyReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {
    suspend operator fun invoke(date: String, parentId: Long? = null): Result<DailyReport?> {
        return reportRepository.fetchDailyReport(date, parentId)
    }
}
