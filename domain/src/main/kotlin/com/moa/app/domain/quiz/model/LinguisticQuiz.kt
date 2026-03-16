package com.moa.app.domain.quiz.model

import kotlinx.collections.immutable.ImmutableList

data class LinguisticQuiz(
    override val id: Long,
    override val type: QuizCategory,
    override val questionFormat: String,
    override val questionContent: String,
    val answer: String,
    val questionImage: String,
    val answerOptions: ImmutableList<String>,
) : Quiz {
    override fun isAnswerCorrect(userAnswer: UserAnswer): Boolean {
        return when (userAnswer) {
            is UserAnswer.Selection -> {
                val selectedOption = answerOptions.getOrNull(userAnswer.index)
                answer == selectedOption
            }

            else -> false
        }
    }
}
