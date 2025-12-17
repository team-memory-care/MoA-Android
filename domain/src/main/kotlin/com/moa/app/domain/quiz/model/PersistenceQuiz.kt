package com.moa.app.domain.quiz.model

import kotlinx.collections.immutable.ImmutableList

data class PersistenceQuiz(
    override val id: Long,
    override val type: QuizCategory,
    override val questionFormat: String,
    override val questionContent: String,
    override val answer: String,
    val answerOptions: ImmutableList<String>,
) : Quiz {
    fun isAnswerCorrect(selectedAnswerIndex: Int): Boolean {
        if (selectedAnswerIndex !in answerOptions.indices) return false
        return answer == answerOptions[selectedAnswerIndex]
    }
}
