package com.moa.app.feature.senior.quiz.category.model

sealed interface QuizCategorySideEffect {
    data class ShowToast(val message: String) : QuizCategorySideEffect
}
