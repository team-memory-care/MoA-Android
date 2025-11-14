package com.moa.app.domain.quiz.model

import kotlinx.collections.immutable.ImmutableList

data class Quiz(
    val question: String,
    val options: ImmutableList<String>,
    val correctAnswerIndex: Int,
) {
    fun isCorrect(selectedIndex: Int?): Boolean {
        return selectedIndex == correctAnswerIndex
    }

    fun getCorrectAnswer(): String {
        return options[correctAnswerIndex]
    }
}
