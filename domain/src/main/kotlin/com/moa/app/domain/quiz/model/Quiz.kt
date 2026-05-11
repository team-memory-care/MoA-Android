package com.moa.app.domain.quiz.model

sealed interface Quiz {
    val id: Long
    val questionFormat: String
    val questionContent: String

    fun isAnswerCorrect(userAnswer: UserAnswer): Boolean
}
