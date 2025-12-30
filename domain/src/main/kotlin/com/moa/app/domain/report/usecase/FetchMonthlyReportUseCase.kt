package com.moa.app.domain.report.usecase

import com.moa.app.domain.report.model.MonthlyReport
import com.moa.app.domain.report.repository.ReportRepository
import javax.inject.Inject

class FetchMonthlyReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {
    suspend operator fun invoke(year: Int, month: Int): Result<MonthlyReport?> {
        return reportRepository.fetchMonthlyReport(year, month)
    }
}
