package com.moa.app.domain.quiz.model

data class QuizScore(
    val totalNumber: Int,
    val correctNumber: Int,
    val type: QuizCategory,
    val category: String = "PRACTICE",
)
