package com.moa.app.data.report.service

import com.moa.app.data.report.model.response.DailyReportResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ReportService {

    @GET("/api/v1/report/daily")
    suspend fun fetchDailyReport(
        @Query("date") date: String,
    ): NetworkResult<DailyReportResponse>

}
