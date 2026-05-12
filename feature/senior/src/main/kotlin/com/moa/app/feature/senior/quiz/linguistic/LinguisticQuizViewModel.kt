package com.moa.app.feature.senior.quiz.linguistic

import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.LinguisticQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.model.UserAnswer
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.internal.QUIZ_RESULT_DISPLAY_MS
import com.moa.app.feature.senior.quiz.internal.loadQuizzesWithMinDelay
import com.moa.app.feature.senior.quiz.linguistic.model.LinguisticQuizUiState
import com.moa.app.feature.senior.quiz.model.QuizResult
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
class LinguisticQuizViewModel @Inject constructor(
    private val navigator: Navigator,
    ttsManager: TtsManager,
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
) : TtsAwareViewModel(ttsManager) {

    private val _uiState = MutableStateFlow(LinguisticQuizUiState.INIT)
    val uiState: StateFlow<LinguisticQuizUiState> = _uiState.asStateFlow()

    init {
        loadLinguisticQuizzes()
    }

    private fun loadLinguisticQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            loadQuizzesWithMinDelay<LinguisticQuiz>(QuizCategory.LINGUISTIC, fetchQuizUseCase)
                .fold(
                    onSuccess = { quizzes ->
                        _uiState.update { it.copy(isLoading = false, quizzes = quizzes) }
                    },
                    onFailure = { t ->
                        Timber.e(t, "loadLinguisticQuizzes failed")
                        _uiState.update { it.copy(isLoading = false) }
                    }
                )
        }
    }

    fun speakCurrentQuestion() {
        if (_uiState.value.currentQuiz == null) return
        ttsManager.speak("아래의 그림은 무엇일까요?")
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
            val correctAnswer = if (isCorrect) "" else quiz.answer

            state.copy(
                showResultDialog = true,
                quizResult = QuizResult(isCorrect = isCorrect, correctAnswer = correctAnswer),
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
                type = QuizCategory.LINGUISTIC,
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
