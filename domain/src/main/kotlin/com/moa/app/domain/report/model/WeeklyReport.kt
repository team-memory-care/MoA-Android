package com.moa.app.domain.report.model

import com.moa.app.domain.quiz.model.QuizCategory

data class WeeklyReport(
    val oneLineReview: String,
    val completeRate: Int,
    val correctRate: Int,
    val diagnosis: String,
    val scores: Map<QuizCategory, List<WeeklyScore>>,
    val nextWeekStrategy: String,
)

data class WeeklyScore(
    val dayOfWeek: String,
    val score: Long,
    val lastWeekScore: Long,
)
