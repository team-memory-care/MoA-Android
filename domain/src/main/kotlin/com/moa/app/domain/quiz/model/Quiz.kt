package com.moa.app.domain.quiz.model

sealed interface Quiz {
    val id: Long
    val type: QuizCategory
    val questionFormat: String
    val questionContent: String

    fun isAnswerCorrect(userAnswer: UserAnswer): Boolean
}
