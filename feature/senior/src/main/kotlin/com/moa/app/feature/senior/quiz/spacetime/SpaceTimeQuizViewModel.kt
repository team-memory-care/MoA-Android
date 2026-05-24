package com.moa.app.feature.senior.quiz.spacetime

import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.model.SpaceTimeQuiz
import com.moa.app.domain.quiz.model.UserAnswer
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.internal.QUIZ_RESULT_DISPLAY_MS
import com.moa.app.feature.senior.quiz.internal.QuizImagePreloader
import com.moa.app.feature.senior.quiz.internal.loadQuizzesWithMinDelay
import com.moa.app.feature.senior.quiz.internal.QuizResult
import com.moa.app.feature.senior.quiz.spacetime.model.SpaceTimeQuizUiState
import com.moa.app.feature.senior.quiz.tts.TtsAwareViewModel
import com.moa.app.feature.senior.quiz.tts.TtsManager
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
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
    ttsManager: TtsManager,
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
    private val imagePreloader: QuizImagePreloader,
) : TtsAwareViewModel(ttsManager) {

    private val _uiState = MutableStateFlow(SpaceTimeQuizUiState.INIT)
    val uiState: StateFlow<SpaceTimeQuizUiState> = _uiState.asStateFlow()
    private val preloadJobs = mutableMapOf<Int, Deferred<Boolean>>()

    init {
        loadSpaceTimeQuizzes()
    }

    private fun loadSpaceTimeQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            loadQuizzesWithMinDelay<SpaceTimeQuiz>(QuizCategory.SPACETIME, fetchQuizUseCase) { quizzes ->
                preloadQuestion(quizzes.firstOrNull())
            }
                .fold(
                    onSuccess = { quizzes ->
                        _uiState.update { it.copy(isLoading = false, quizzes = quizzes) }
                        startPreloadForQuestion(1, quizzes)
                    },
                    onFailure = { t ->
                        Timber.e(t, "loadSpaceTimeQuizzes failed")
                        _uiState.update { it.copy(isLoading = false) }
                    }
                )
        }
    }

    fun speakCurrentQuestion() {
        if (_uiState.value.currentQuiz == null) return
        ttsManager.speak("겹치는 모양을 찾아주세요!")
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
            delay(QUIZ_RESULT_DISPLAY_MS)
            goToNextQuestion()
        }
    }

    private suspend fun goToNextQuestion() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex >= currentState.quizzes.size) {
            uploadQuizResult(currentState.correctCount, currentState.quizzes.size)
        } else {
            waitForPreload(nextIndex, currentState.quizzes)
            _uiState.update { state ->
                state.copy(
                    currentQuestionIndex = nextIndex,
                    selectedAnswerIndex = null,
                    showResultDialog = false,
                    quizResult = null,
                )
            }
            startPreloadForQuestion(nextIndex + 1, currentState.quizzes)
        }
    }

    private suspend fun waitForPreload(index: Int, quizzes: ImmutableList<SpaceTimeQuiz>) {
        val job = preloadJobs[index] ?: startPreloadForQuestion(index, quizzes) ?: return
        job.await()
    }

    private fun startPreloadForQuestion(
        index: Int,
        quizzes: ImmutableList<SpaceTimeQuiz> = _uiState.value.quizzes,
    ): Deferred<Boolean>? {
        if (index !in quizzes.indices) return null
        preloadJobs[index]?.let { return it }

        return viewModelScope.async {
            preloadQuestion(quizzes[index])
        }.also { preloadJobs[index] = it }
    }

    private suspend fun preloadQuestion(quiz: SpaceTimeQuiz?): Boolean {
        quiz ?: return true
        val urls = listOf(quiz.questionImageUrl) + quiz.imageOptionsUrl
        return imagePreloader.preload(urls, QuizCategory.SPACETIME)
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
