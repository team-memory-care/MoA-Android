package com.moa.app.data.report.service

import com.moa.app.data.report.model.response.DailyReportResponse
import com.moa.app.data.report.model.response.MonthlyReportResponse
import com.moa.app.data.report.model.response.WeeklyReportResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ReportService {

    @GET("/api/v1/report/daily")
    suspend fun fetchDailyReport(
        @Query("date") date: String,
    ): NetworkResult<DailyReportResponse?>

    @GET("/api/v1/report/weekly")
    suspend fun fetchWeeklyReport(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("week") week: Int,
    ): NetworkResult<WeeklyReportResponse?>

    @GET("/api/v1/report/monthly")
    suspend fun fetchMonthlyReport(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): NetworkResult<MonthlyReportResponse?>

}
