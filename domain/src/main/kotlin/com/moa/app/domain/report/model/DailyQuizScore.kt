package com.moa.app.domain.report.model

import com.moa.app.domain.quiz.model.QuizCategory

data class DailyQuizScore(
    val type: QuizCategory,
    val correctNumber: Int,
    val totalNumber: Int,
)
