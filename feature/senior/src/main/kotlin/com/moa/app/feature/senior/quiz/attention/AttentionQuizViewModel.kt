package com.moa.app.feature.senior.quiz.attention

import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.AttentionQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.model.UserAnswer
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.attention.model.AttentionQuizUiState
import com.moa.app.feature.senior.quiz.internal.QUIZ_RESULT_DISPLAY_MS
import com.moa.app.feature.senior.quiz.internal.loadQuizzesWithMinDelay
import com.moa.app.feature.senior.quiz.internal.QuizResult
import com.moa.app.feature.senior.quiz.tts.QuizTextNormalizer
import com.moa.app.feature.senior.quiz.tts.TtsAwareViewModel
import com.moa.app.feature.senior.quiz.tts.TtsManager
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
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
    ttsManager: TtsManager,
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
) : TtsAwareViewModel(ttsManager) {

    private val _uiState = MutableStateFlow(AttentionQuizUiState.INIT)
    val uiState: StateFlow<AttentionQuizUiState> = _uiState.asStateFlow()

    init {
        loadAttentionQuizzes()
    }

    private fun loadAttentionQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            loadQuizzesWithMinDelay<AttentionQuiz>(QuizCategory.ATTENTION, fetchQuizUseCase)
                .fold(
                    onSuccess = { quizzes ->
                        _uiState.update { it.copy(isLoading = false, quizzes = quizzes) }
                    },
                    onFailure = { t ->
                        Timber.e(t, "loadAttentionQuizzes failed")
                        _uiState.update { it.copy(isLoading = false) }
                    }
                )
        }
    }

    fun speakCurrentQuestion() {
        val quiz = _uiState.value.currentQuiz ?: return
        val normalizedText = QuizTextNormalizer.normalizeExpression(quiz.expression + "=")
        ttsManager.speak(normalizedText)
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
        if (ttsManager.isSpeaking) ttsManager.stop()
        _uiState.update { state ->
            if (state.userAnswer.isEmpty()) return@update state
            val currentQuiz = state.currentQuiz ?: return@update state
            val isCorrect = currentQuiz.isAnswerCorrect(UserAnswer.Text(state.userAnswer))
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
            delay(QUIZ_RESULT_DISPLAY_MS)
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
