package com.moa.app.data.report.datasourceImpl

import com.moa.app.data.report.datasource.ReportDataSource
import com.moa.app.data.report.model.response.DailyReportResponse
import com.moa.app.data.report.service.ReportService
import com.moa.app.network.extension.toResult
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService
) : ReportDataSource {

    override suspend fun fetchDailyReport(date: String): Result<DailyReportResponse> {
        return reportService.fetchDailyReport(date).toResult { it }
    }

}
