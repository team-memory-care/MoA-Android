package com.moa.app.feature.senior.quiz.spacetime.model

import androidx.compose.runtime.Immutable
import com.moa.app.domain.quiz.model.SpaceTimeQuiz
import com.moa.app.feature.senior.quiz.model.QuizResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SpaceTimeQuizUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val quizzes: ImmutableList<SpaceTimeQuiz>,
    val currentQuestionIndex: Int,
    val selectedAnswerIndex: Int?,
    val quizResult: QuizResult?,
    val showResultDialog: Boolean,
    val showExitDialog: Boolean,
    val correctCount: Int,
) {
    val currentQuiz: SpaceTimeQuiz?
        get() = quizzes.getOrNull(currentQuestionIndex)

    val totalSteps: Int
        get() = quizzes.size

    val currentStep: Int
        get() = currentQuestionIndex + 1

    val isContinueButtonEnabled: Boolean
        get() = (selectedAnswerIndex != null) && !showResultDialog


    companion object {
        val INIT = SpaceTimeQuizUiState(
            isLoading = true,
            errorMessage = null,
            quizzes = persistentListOf(),
            currentQuestionIndex = 0,
            selectedAnswerIndex = null,
            quizResult = null,
            showResultDialog = false,
            showExitDialog = false,
            correctCount = 0
        )
    }
}
