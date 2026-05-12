package com.moa.app.feature.senior.quiz.attention.model

import com.moa.app.domain.quiz.model.AttentionQuiz
import com.moa.app.feature.senior.quiz.internal.QuizResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AttentionQuizUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val quizzes: ImmutableList<AttentionQuiz>,
    val currentQuestionIndex: Int,
    val userAnswer: String,
    val quizResult: QuizResult?,
    val showResultDialog: Boolean,
    val showExitDialog: Boolean,
    val correctCount: Int,
) {
    val currentQuiz: AttentionQuiz?
        get() = quizzes.getOrNull(currentQuestionIndex)

    val totalSteps: Int
        get() = quizzes.size

    val currentStep: Int
        get() = currentQuestionIndex + 1

    val isContinueButtonEnabled: Boolean
        get() = (userAnswer.length == currentQuiz?.answer?.length) && !showResultDialog

    companion object {
        val INIT = AttentionQuizUiState(
            isLoading = false,
            errorMessage = null,
            quizzes = persistentListOf(),
            currentQuestionIndex = 0,
            userAnswer = "",
            quizResult = null,
            showResultDialog = false,
            showExitDialog = false,
            correctCount = 0,
        )
    }
}
