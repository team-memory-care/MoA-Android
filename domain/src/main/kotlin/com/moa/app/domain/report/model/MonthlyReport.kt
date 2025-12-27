package com.moa.app.domain.report.model

import com.moa.app.domain.quiz.model.QuizCategory

data class MonthlyReport(
    val oneLineReview: String,
    val completeRate: Int,
    val correctRate: Int,
    val diagnosis: String,
    val score: Map<QuizCategory, MonthReportScore>,
    val longTermStrategy: String,
)

data class MonthReportScore(
    val weekIndex: Int,
    val score: Long,
    val lastMonthScore: Long,
)
