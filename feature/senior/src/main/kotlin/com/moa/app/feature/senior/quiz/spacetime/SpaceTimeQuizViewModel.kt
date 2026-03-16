package com.moa.app.feature.senior.quiz.spacetime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.model.SpaceTimeQuiz
import com.moa.app.domain.quiz.model.UserAnswer
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.model.QuizResult
import com.moa.app.feature.senior.quiz.spacetime.model.SpaceTimeQuizUiState
import com.moa.app.feature.senior.quiz.tts.TtsManager
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
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
class SpaceTimeQuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val ttsManager: TtsManager,
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpaceTimeQuizUiState.INIT)
    val uiState: StateFlow<SpaceTimeQuizUiState> = _uiState.asStateFlow()

    init {
        loadSpaceTimeQuizzes()
    }

    fun speakCurrentQuestion() {
        if (_uiState.value.currentQuiz == null) return
        ttsManager.speak("겹치는 모양을 찾아주세요!")
    }

    private fun loadSpaceTimeQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val minLoadingTime = async { delay(2000L) }
            val quizzesDeferred = async { fetchQuizUseCase(QuizCategory.SPACETIME) }
            awaitAll(minLoadingTime, quizzesDeferred)
            quizzesDeferred.await().fold(
                onSuccess = { quizzes ->
                    val spaceTimeQuizzes = quizzes.filterIsInstance<SpaceTimeQuiz>()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quizzes = spaceTimeQuizzes.toImmutableList()
                        )
                    }
                },
                onFailure = { t ->
                    Timber.e(t, "loadSpaceTimeQuizzes failed")
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        }
    }

    fun selectAnswer(index: Int) {
        _uiState.update { state ->
            if (state.showResultDialog || state.isLoading) return@update state
            state.copy(selectedAnswerIndex = index)
        }
    }

    fun checkAnswer() {
        if (ttsManager.isSpeaking) ttsManager.stop()
        _uiState.update { state ->
            val selectedAnswerIndex = state.selectedAnswerIndex ?: return@update state
            val quiz = state.currentQuiz ?: return@update state
            val isCorrect = quiz.isAnswerCorrect(UserAnswer.Selection(selectedAnswerIndex))
            val correctAnswer = if (isCorrect) "" else "다른 모양"

            state.copy(
                showResultDialog = true,
                quizResult = QuizResult(isCorrect = isCorrect, correctAnswer = correctAnswer),
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
                    selectedAnswerIndex = null,
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
                type = QuizCategory.SPACETIME,
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

    fun onBackClick() {
        _uiState.update { it.copy(showExitDialog = true) }
    }

    fun onHideExitDialog() {
        _uiState.update { it.copy(showExitDialog = false) }
    }

    fun exitQuiz() = navigator.navigateBack()

}
