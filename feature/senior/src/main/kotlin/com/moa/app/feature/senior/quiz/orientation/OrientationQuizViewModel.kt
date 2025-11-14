package com.moa.app.feature.senior.quiz.orientation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.Quizzes
import com.moa.app.domain.quiz.usecase.CheckAnswerUseCase
import com.moa.app.domain.quiz.usecase.FetchOrientationQuizUseCase
import com.moa.app.feature.senior.quiz.component.ResultDialogState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrientationQuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchOrientationQuizUseCase: FetchOrientationQuizUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<OrientationQuizUiState> = MutableStateFlow(OrientationQuizUiState.Loading)
    val uiState: StateFlow<OrientationQuizUiState> = _uiState.asStateFlow()

    private var quizzes: Quizzes = Quizzes.from(persistentListOf())

    init {
        loadQuizzes()
    }

    private fun loadQuizzes() {
        viewModelScope.launch {
            delay(2000L)
            val quizzes = fetchOrientationQuizUseCase()
            this@OrientationQuizViewModel.quizzes = quizzes
            val quizzesUiModel = quizzes.map { it.toUiModel() }

            if (quizzesUiModel.isNotEmpty()) {
                _uiState.update { OrientationQuizUiState.Success(quizzes = quizzesUiModel) }
            } else {
                _uiState.update { OrientationQuizUiState.Error(message = "퀴즈가 존재하지 않습니다") }
            }
        }
    }

    fun selectAnswer(selectedAnswerIndex: Int) {
        _uiState.update { currentState ->
            if (currentState is OrientationQuizUiState.Success && !currentState.isCheckingAnswer) {
                currentState.copy(selectedAnswerIndex = selectedAnswerIndex)
            } else {
                currentState
            }
        }
    }

    fun checkAnswer() {
        _uiState.update { currentState ->
            if (currentState !is OrientationQuizUiState.Success || currentState.isCheckingAnswer) return@update currentState
            val currentDomainQuestion = this.quizzes.getQuizAt(currentState.currentQuestionIndex) ?: return@update currentState
            val isCorrect = checkAnswerUseCase(
                quiz = currentDomainQuestion,
                selectedIndex = currentState.selectedAnswerIndex,
            )

            val newDialogState = if (isCorrect) {
                ResultDialogState.Correct
            } else {
                val correctAnswer = currentDomainQuestion.getCorrectAnswer()
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
            if (currentState !is OrientationQuizUiState.Success) return@update currentState

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
                    popUpTo = AppRoute.OrientationQuiz,
                    inclusive = true,
                    launchSingleTop = true
                )
            )
        }
    }

    companion object {
        private const val DIALOG_DURATION_MS = 2000L
    }
}

@Immutable
sealed interface OrientationQuizUiState {
    data object Loading : OrientationQuizUiState
    data class Error(val message: String) : OrientationQuizUiState
    data class Success(
        val quizzes: ImmutableList<QuizUiModel>,
        val currentQuestionIndex: Int = 0,
        val selectedAnswerIndex: Int? = null,
        val resultDialogState: ResultDialogState = ResultDialogState.Hidden,
    ) : OrientationQuizUiState {
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

fun Quiz.toUiModel() =
    QuizUiModel(
        question = this.question,
        options = this.options,
    )
