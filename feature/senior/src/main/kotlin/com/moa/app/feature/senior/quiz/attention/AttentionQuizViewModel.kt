package com.moa.app.feature.senior.quiz.attention

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.AttentionQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.attention.model.AttentionQuizUiState
import com.moa.app.feature.senior.quiz.model.QuizResult
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AttentionQuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AttentionQuizUiState.INIT)
    val uiState: StateFlow<AttentionQuizUiState> = _uiState.asStateFlow()

    init {
        loadAttentionQuizzes()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadAttentionQuizzes() {
        viewModelScope.launch {
            val minLoadingTime = async { delay(2000L) }
            val quizzesDeferred = async {
                fetchQuizUseCase(QuizCategory.ATTENTION)
            }
            awaitAll(minLoadingTime, quizzesDeferred)
            quizzesDeferred.getCompleted().fold(
                onSuccess = { quizzes ->
                    val attentionQuizzes = quizzes.filterIsInstance<AttentionQuiz>()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quizzes = attentionQuizzes.toImmutableList()
                        )
                    }
                },
                onFailure = { t ->
                    Timber.e(t, "loadAttentionQuizzes failed")
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }

    fun updateUserInput(input: String) {
        _uiState.update { state ->
            val currentQuiz = state.currentQuiz ?: return@update state
            if ((state.userAnswer + input).length > currentQuiz.answer.length) {
                return@update state
            }
            state.copy(userAnswer = state.userAnswer + input)
        }
    }

    fun checkAnswer() {
        _uiState.update { state ->
            if (state.userAnswer.isEmpty()) return@update state
            val currentQuiz = state.currentQuiz ?: return@update state
            val isCorrect = currentQuiz.isAnswerCorrect(state.userAnswer)
            val quizResult = QuizResult(
                isCorrect = isCorrect,
                correctAnswer = if (isCorrect) "" else currentQuiz.answer,
            )

            state.copy(
                showResultDialog = true,
                quizResult = quizResult,
                correctCount = if (isCorrect) state.correctCount + 1 else state.correctCount,
            )
        }

        viewModelScope.launch {
            delay(2000L)
            goToNextQuestion()
        }
    }

    private fun goToNextQuestion() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex >= currentState.quizzes.size) {
            uploadQuizResult(currentState.correctCount, currentState.quizzes.size)
        } else {
            _uiState.update { state ->
                state.copy(
                    currentQuestionIndex = nextIndex,
                    userAnswer = "",
                    showResultDialog = false,
                    quizResult = null,
                )
            }
        }
    }

    private fun uploadQuizResult(correctCount: Int, totalCount: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = QuizScore(
                totalNumber = totalCount,
                correctNumber = correctCount,
                type = QuizCategory.ATTENTION,
            )

            uploadQuizScoreUseCase(result)
                .onSuccess { exitQuiz() }
                .onFailure { t ->
                    Timber.e(t, "Failed to submit quiz result")
                    _uiState.update { it.copy(isLoading = false, errorMessage = "결과 전송 실패") }
                    exitQuiz()
                }
        }
    }

    fun clearUserInput() {
        _uiState.update { it.copy(userAnswer = "") }
    }

    fun onBackClick() {
        _uiState.update { it.copy(showExitDialog = true) }
    }

    fun onHideExitDialog() {
        _uiState.update { it.copy(showExitDialog = false) }
    }

    fun exitQuiz() = navigator.navigateBack()
}


