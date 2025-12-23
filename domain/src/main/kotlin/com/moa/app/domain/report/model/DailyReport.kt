package com.moa.app.domain.report.model

data class DailyReport(
    val date: String,
    val dailyQuizScore: List<DailyQuizScore>,
    val advices: List<String>,
)
