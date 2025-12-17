package com.moa.app.feature.senior.quiz.linguistic.model

import androidx.compose.runtime.Immutable
import com.moa.app.domain.quiz.model.LinguisticQuiz
import com.moa.app.feature.senior.quiz.model.QuizResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class LinguisticQuizUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val quizzes: ImmutableList<LinguisticQuiz>,
    val currentQuestionIndex: Int,
    val selectedAnswerIndex: Int?,
    val quizResult: QuizResult?,
    val showResultDialog: Boolean,
    val showExitDialog: Boolean,
    val correctCount: Int,
) {
    val currentQuiz: LinguisticQuiz?
        get() = quizzes.getOrNull(currentQuestionIndex)

    val totalSteps: Int
        get() = quizzes.size

    val currentStep: Int
        get() = currentQuestionIndex + 1

    companion object {
        val INIT = LinguisticQuizUiState(
            isLoading = true,
            errorMessage = null,
            quizzes = persistentListOf(),
            currentQuestionIndex = 0,
            selectedAnswerIndex = null,
            quizResult = null,
            showResultDialog = false,
            showExitDialog = false,
            correctCount = 0,
        )
    }
}
