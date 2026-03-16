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
    override fun isAnswerCorrect(userAnswer: UserAnswer): Boolean {
        return when (userAnswer) {
            is UserAnswer.Selection -> {
                if (userAnswer.index !in imageOptionsUrl.indices) return false
                (userAnswer.index + 1).toString() == answer
            }

            else -> false
        }
    }
}
