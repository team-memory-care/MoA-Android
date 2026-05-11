package com.moa.app.domain.quiz.model

data class AttentionQuiz(
    override val id: Long,
    override val questionFormat: String,
    override val questionContent: String,
    val answer: String,
    val expression: String,
    val inputType: String,
) : Quiz {
    override fun isAnswerCorrect(userAnswer: UserAnswer): Boolean {
        return when (userAnswer) {
            is UserAnswer.Text -> userAnswer.answer == answer
            else -> false
        }
    }
}
