package com.moa.app.domain.quiz.model

import kotlinx.collections.immutable.ImmutableList

data class SpaceTimeQuiz(
    override val id: Long,
    override val questionFormat: String,
    override val questionContent: String,
    val answerIndex: Int,
    val questionImageUrl: String,
    val imageOptionsUrl: ImmutableList<String>,
) : Quiz {
    override fun isAnswerCorrect(userAnswer: UserAnswer): Boolean {
        return when (userAnswer) {
            is UserAnswer.Selection -> userAnswer.index == answerIndex
            else -> false
        }
    }
}
