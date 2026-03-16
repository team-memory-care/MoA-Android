package com.moa.app.domain.quiz.model

sealed interface UserAnswer {
    data class Text(val answer: String) : UserAnswer
    data class Selection(val index: Int) : UserAnswer
    data class MultipleText(val answers: List<String>) : UserAnswer
}
