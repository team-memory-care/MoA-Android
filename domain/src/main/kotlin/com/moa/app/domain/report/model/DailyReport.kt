package com.moa.app.domain.report.model

import com.moa.app.domain.quiz.model.QuizCategory
import kotlinx.collections.immutable.ImmutableList

data class DailyReport(
    val date: String,
    val dailyQuizScore: ImmutableList<DailyQuizScore>,
    val advices: ImmutableList<String>,
)

data class DailyQuizScore(
    val type: QuizCategory,
    val correctNumber: Int,
    val totalNumber: Int,
)
