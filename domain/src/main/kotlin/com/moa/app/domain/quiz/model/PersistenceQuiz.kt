package com.moa.app.domain.quiz.model

import kotlinx.collections.immutable.ImmutableList

data class PersistenceQuiz(
    val id: Long,
    val type: QuizCategory,
    val questionFormat: String,
    val questionContent: String,
    val answer: String,
    val answerOptions: ImmutableList<String>,
) {
    fun getCurrentAnswerIndex(selectedAnswerIndex: Int): Boolean {
        return selectedAnswerIndex == answerOptions.indexOf(answer)
    }
}
