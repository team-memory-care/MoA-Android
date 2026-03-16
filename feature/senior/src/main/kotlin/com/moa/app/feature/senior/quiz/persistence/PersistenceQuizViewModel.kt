package com.moa.app.feature.senior.quiz.persistence

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.model.UserAnswer
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.model.QuizResult
import com.moa.app.feature.senior.quiz.tts.TtsManager
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
class PersistenceQuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val ttsManager: TtsManager,
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersistenceQuizUiState.INIT)
    val uiState: StateFlow<PersistenceQuizUiState> = _uiState.asStateFlow()

    init {
        loadPersistenceQuizzes()
    }

    fun speakCurrentQuestion() {
        val currentQuiz = _uiState.value.currentQuiz ?: return
        ttsManager.speak(currentQuiz.questionContent)
    }

    private fun loadPersistenceQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val minLoadingTime = async { delay(2000L) }
            val quizzesDeferred = async { fetchQuizUseCase(QuizCategory.PERSISTENCE) }
            awaitAll(minLoadingTime, quizzesDeferred)
            quizzesDeferred.await().fold(
                onSuccess = { quizzes ->
                    val persistenceQuizzes = quizzes.filterIsInstance<PersistenceQuiz>()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quizzes = persistenceQuizzes.toImmutableList(),
                        )
                    }
                },
                onFailure = { t ->
                    Timber.e("loadQuizzes failed: $t")
                    _uiState.update { it.copy(isLoading = false) }
                },
            )
        }
    }

    fun selectAnswer(selectedAnswerIndex: Int) {
        _uiState.update {
            if (it.showResultDialog || it.isLoading) return@update it
            it.copy(selectedAnswerIndex = selectedAnswerIndex)
        }
    }

    fun checkAnswer() {
        if (ttsManager.isSpeaking) ttsManager.stop()
        _uiState.update {
            if (it.showResultDialog || it.isLoading) return@update it
            val selectedAnswerIndex = it.selectedAnswerIndex ?: return@update it
            val currentQuiz = it.quizzes.getOrNull(it.currentQuestionIndex) ?: return@update it
            val isCorrect = currentQuiz.isAnswerCorrect(UserAnswer.Selection(selectedAnswerIndex))
            val correctAnswer = if (isCorrect) "" else currentQuiz.answer

            it.copy(
                showResultDialog = true,
                quizResult = QuizResult(isCorrect = isCorrect, correctAnswer = correctAnswer),
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
            )
        }

        viewModelScope.launch {
            delay(DIALOG_DURATION_MS)
            goToNextQuestion()
        }
    }

    private fun goToNextQuestion() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex >= currentState.quizzes.size) {
            submitQuizResult(currentState.correctCount, currentState.quizzes.size)
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

    private fun submitQuizResult(correctCount: Int, totalCount: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = QuizScore(
                totalNumber = totalCount,
                correctNumber = correctCount,
                type = QuizCategory.PERSISTENCE,
            )

            uploadQuizScoreUseCase(result)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    exitQuiz()
                }
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

    override fun onCleared() {
        super.onCleared()
        ttsManager.destroy()
    }

    companion object {
        private const val DIALOG_DURATION_MS = 2000L
    }
}

@Immutable
data class PersistenceQuizUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val quizzes: ImmutableList<PersistenceQuiz>,
    val currentQuestionIndex: Int,
    val selectedAnswerIndex: Int?,
    val showResultDialog: Boolean,
    val showExitDialog: Boolean,
    val quizResult: QuizResult?,
    val correctCount: Int,
) {
    val currentQuiz: PersistenceQuiz?
        get() = quizzes.getOrNull(currentQuestionIndex)

    val currentStep: Int
        get() = currentQuestionIndex + 1

    val totalSteps: Int
        get() = quizzes.size

    val isContinueButtonEnabled: Boolean
        get() = selectedAnswerIndex != null && !showResultDialog

    companion object {
        val INIT = PersistenceQuizUiState(
            isLoading = false,
            errorMessage = null,
            quizzes = persistentListOf(),
            currentQuestionIndex = 0,
            selectedAnswerIndex = null,
            showResultDialog = false,
            showExitDialog = false,
            quizResult = null,
            correctCount = 0,
        )
    }
}
