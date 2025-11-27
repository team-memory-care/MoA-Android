package com.moa.app.feature.senior.quiz.category.model

import com.moa.app.domain.quiz.model.QuizCategory

data class QuizCategoryUiState(
    val enabledCategories: Set<QuizCategory>
) {
    companion object {
        val INIT = QuizCategoryUiState(
            enabledCategories = emptySet()
        )
    }
}
