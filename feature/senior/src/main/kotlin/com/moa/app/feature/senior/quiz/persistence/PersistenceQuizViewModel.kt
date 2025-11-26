package com.moa.app.feature.senior.quiz.persistence

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.usecase.CheckAnswerUseCase
import com.moa.app.domain.quiz.usecase.FetchOrientationQuizUseCase
import com.moa.app.feature.senior.quiz.component.ResultDialogState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersistenceQuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchOrientationQuizUseCase: FetchOrientationQuizUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<PersistenceQuizUiState> = MutableStateFlow(PersistenceQuizUiState.Loading)
    val uiState: StateFlow<PersistenceQuizUiState> = _uiState.asStateFlow()

    private var quizzes: List<PersistenceQuiz> = emptyList()

    init {
        loadQuizzes()
    }

    private fun loadQuizzes() {
        viewModelScope.launch {
            delay(2000L)
            val quizzes = fetchOrientationQuizUseCase()
            this@PersistenceQuizViewModel.quizzes = quizzes
            val quizzesUiModel = quizzes.map { it.toUiModel() }.toImmutableList()

            if (quizzesUiModel.isNotEmpty()) {
                _uiState.update { PersistenceQuizUiState.Success(quizzes = quizzesUiModel) }
            } else {
                _uiState.update { PersistenceQuizUiState.Error(message = "퀴즈가 존재하지 않습니다") }
            }
        }
    }

    fun selectAnswer(selectedAnswerIndex: Int) {
        _uiState.update { currentState ->
            if (currentState is PersistenceQuizUiState.Success && !currentState.isCheckingAnswer) {
                currentState.copy(selectedAnswerIndex = selectedAnswerIndex)
            } else {
                currentState
            }
        }
    }

    fun checkAnswer() {
        _uiState.update { currentState ->
            if (currentState !is PersistenceQuizUiState.Success || currentState.isCheckingAnswer) return@update currentState
            val currentDomainQuestion = this.quizzes.getOrNull(currentState.currentQuestionIndex) ?: return@update currentState
            val isCorrect = checkAnswerUseCase(
                quiz = currentDomainQuestion,
                selectedIndex = currentState.selectedAnswerIndex,
            )

            val newDialogState = if (isCorrect) {
                ResultDialogState.Correct
            } else {
                val correctAnswer = currentDomainQuestion.answer
                ResultDialogState.Incorrect(correctAnswer)
            }

            currentState.copy(
                resultDialogState = newDialogState,
            )
        }

        viewModelScope.launch {
            delay(DIALOG_DURATION_MS)
            goToNextQuestion()
        }
    }

    private fun goToNextQuestion() {
        var shouldNavigate = false

        _uiState.update { currentState ->
            if (currentState !is PersistenceQuizUiState.Success) return@update currentState

            val nextIndex = currentState.currentQuestionIndex + 1

            if (nextIndex < this.quizzes.size) {
                currentState.copy(
                    currentQuestionIndex = nextIndex,
                    selectedAnswerIndex = null,
                    resultDialogState = ResultDialogState.Hidden,
                )
            } else {
                shouldNavigate = true
                currentState.copy(
                    resultDialogState = ResultDialogState.Hidden,
                )
            }
        }

        if (shouldNavigate) {
            navigator.navigate(
                route = AppRoute.SeniorHome,
                options = NavigationOptions(
                    popUpTo = AppRoute.PersistenceQuiz,
                    inclusive = true,
                    launchSingleTop = true
                )
            )
        }
    }

    companion object Companion {
        private const val DIALOG_DURATION_MS = 2000L
    }
}

@Immutable
sealed interface PersistenceQuizUiState {
    data object Loading : PersistenceQuizUiState
    data class Error(val message: String) : PersistenceQuizUiState
    data class Success(
        val quizzes: ImmutableList<QuizUiModel>,
        val currentQuestionIndex: Int = 0,
        val selectedAnswerIndex: Int? = null,
        val resultDialogState: ResultDialogState = ResultDialogState.Hidden,
    ) : PersistenceQuizUiState {
        val currentStep: Int
            get() = currentQuestionIndex + 1

        val totalSteps: Int
            get() = quizzes.size

        val isCheckingAnswer: Boolean
            get() = resultDialogState != ResultDialogState.Hidden

        val isContinueButtonEnabled: Boolean
            get() = selectedAnswerIndex != null && !isCheckingAnswer
    }
}

data class QuizUiModel(
    val question: String,
    val options: ImmutableList<String>,
)

fun PersistenceQuiz.toUiModel() =
    QuizUiModel(
        question = this.questionFormat,
        options = this.answerOptions,
    )
