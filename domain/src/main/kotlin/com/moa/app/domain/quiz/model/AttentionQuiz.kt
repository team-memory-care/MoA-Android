package com.moa.app.domain.quiz.model

data class AttentionQuiz(
    override val id: Long,
    override val type: QuizCategory,
    override val questionFormat: String,
    override val questionContent: String,
    override val answer: String,
    val expression: String,
    val inputType: String,
) : Quiz {
    fun isAnswerCorrect(userAnswer: String): Boolean {
        return answer == userAnswer
    }
}
