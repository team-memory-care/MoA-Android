package com.moa.app.domain.quiz.model

import kotlinx.collections.immutable.ImmutableList

data class SpaceTimeQuiz(
    override val id: Long,
    override val type: QuizCategory,
    override val questionFormat: String,
    override val questionContent: String,
    val answer: String,
    val questionImageUrl: String,
    val imageOptionsUrl: ImmutableList<String>,
) : Quiz {
    fun isAnswerCorrect(selectedAnswerIndex: Int): Boolean {
        if (selectedAnswerIndex !in imageOptionsUrl.indices) return false
        return (selectedAnswerIndex + 1).toString() == answer
    }
}
