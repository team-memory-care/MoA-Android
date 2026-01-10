package com.moa.app.feature.senior.quiz.daily

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.AttentionQuiz
import com.moa.app.domain.quiz.model.LinguisticQuiz
import com.moa.app.domain.quiz.model.MemoryQuiz
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.model.SpaceTimeQuiz
import com.moa.app.domain.quiz.usecase.FetchDailyQuizzesUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.memory.InputMode
import com.moa.app.feature.senior.quiz.memory.MemoryQuizSetState
import com.moa.app.feature.senior.quiz.model.QuizResult
import com.moa.app.feature.senior.quiz.stt.SttManager
import com.moa.app.feature.senior.quiz.stt.SttState
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
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
class DailyQuizViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val navigator: Navigator,
    private val sttManager: SttManager,
    private val fetchDailyQuizzesUseCase: FetchDailyQuizzesUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyQuizUiState.INIT)
    val uiState: StateFlow<DailyQuizUiState> = _uiState.asStateFlow()

    init {
        observeSttState()
        loadDailyQuizzes()

        when (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)) {
            PackageManager.PERMISSION_GRANTED -> {}
            else -> switchToTextMode()
        }
    }

    private fun loadDailyQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val minLoadingTime = async { delay(2000L) }
            val quizzesDeferred = async { fetchDailyQuizzesUseCase() }
            awaitAll(minLoadingTime, quizzesDeferred)
            quizzesDeferred.await().fold(
                onSuccess = { quizzes ->
                    _uiState.update {
                        it.copy(isLoading = false, quizzes = quizzes)
                    }
                },
                onFailure = { t ->
                    Timber.e(t, "loadDailyQuizzes failed")
                    _uiState.update { it.copy(isLoading = false) }
                },
            )
        }
    }

    fun displayQuizImages() {
        _uiState.update { it.copy(memoryQuizState = MemoryQuizSetState.QUESTION_DISPLAY) }
    }

    fun onImagesFinished() {
        _uiState.update { it.copy(memoryQuizState = MemoryQuizSetState.ANSWERING) }
    }

    private fun observeSttState() {
        viewModelScope.launch {
            sttManager.sttState.collect { state ->
                when (state) {
                    is SttState.Idle -> _uiState.update { it.copy(isSpeaking = false) }
                    is SttState.Speaking -> _uiState.update { it.copy(isSpeaking = true) }
                    is SttState.Success -> {
                        Timber.d("STT Success: ${state.result}")
                        sttManager.stopListening()
                        checkAnswer(state.result)
                    }

                    is SttState.Error -> {
                        _uiState.update {
                            it.copy(isSpeaking = false, errorMessage = state.message)
                        }
                    }
                }
            }
        }
    }

    fun startListening() = sttManager.startListening()

    fun displayChangeModeButton() {
        _uiState.update { it.copy(isChangeModeButtonEnabled = true) }
    }

    fun switchToTextMode() {
        _uiState.update { it.copy(memoryQuizInputMode = InputMode.TEXT) }
    }

    fun updateTextAnswer(index: Int, text: String) {
        _uiState.update { state ->
            val updatedAnswers = state.memoryQuizTextAnswers.set(index, text)

            state.copy(memoryQuizTextAnswers = updatedAnswers)
        }
    }

    fun selectAnswer(selectedAnswerIndex: Int) {
        _uiState.update {
            it.copy(selectedAnswerIndex = selectedAnswerIndex)
        }
    }

    fun updateAnswer(userAnswer: String) {
        _uiState.update {
            val currentQuiz = it.currentQuiz as? AttentionQuiz ?: return@update it
            if ((it.attentionQuizAnswer + userAnswer).length > currentQuiz.answer.length) {
                return@update it
            }
            it.copy(attentionQuizAnswer = it.attentionQuizAnswer + userAnswer)
        }
    }

    fun deleteAnswer() {
        _uiState.update { it.copy(attentionQuizAnswer = "") }
    }

    fun checkAnswer(sttResult: String? = null) {
        if (_uiState.value.isChecking) return

        _uiState.update { state ->
            val currentQuiz = state.currentQuiz ?: return@update state
            val isCorrect = when (currentQuiz) {
                is PersistenceQuiz -> state.selectedAnswerIndex?.let { currentQuiz.isAnswerCorrect(it) } ?: false
                is LinguisticQuiz -> state.selectedAnswerIndex?.let { currentQuiz.isAnswerCorrect(it) } ?: false
                is SpaceTimeQuiz -> state.selectedAnswerIndex?.let { currentQuiz.isAnswerCorrect(it) } ?: false
                is AttentionQuiz -> currentQuiz.isAnswerCorrect(state.attentionQuizAnswer)
                is MemoryQuiz -> {
                    if (sttResult != null) currentQuiz.isAnswerCorrect(sttResult)
                    else currentQuiz.isAnswerCorrect(state.memoryQuizTextAnswers)
                }
            }
            val correctAnswer = if (isCorrect) "" else "다른 값"

            state.copy(
                isChecking = true,
                showResultDialog = true,
                quizResult = QuizResult(isCorrect = isCorrect, correctAnswer = correctAnswer),
                correctCount = if (isCorrect) state.correctCount + 1 else state.correctCount,
            )
        }

        viewModelScope.launch {
            delay(2000L)
            goToNextQuestion()
            _uiState.update { it.copy(isChecking = false) }
        }
    }

    private fun goToNextQuestion() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex >= currentState.quizzes.size) {
            uploadQuizResult(currentState.correctCount, currentState.quizzes.size)
        } else {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    showResultDialog = false,
                    quizResult = null,
                    selectedAnswerIndex = null,
                    attentionQuizAnswer = "",
                    memoryQuizState = MemoryQuizSetState.WAITING_TO_START,
                    memoryQuizTextAnswers = persistentListOf("", "", ""),
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
                type = QuizCategory.ALL,
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

    override fun onCleared() {
        super.onCleared()
        sttManager.destroy()
    }
}


data class DailyQuizUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val isChecking: Boolean = false,
    val quizzes: List<Quiz>,
    val currentQuestionIndex: Int,

    val selectedAnswerIndex: Int? = null, // 지남력, 언어능력, 시공간
    val attentionQuizAnswer: String = "",  // 집중력

    // memory
    val memoryQuizState: MemoryQuizSetState = MemoryQuizSetState.WAITING_TO_START,
    val memoryQuizInputMode: InputMode = InputMode.VOICE,
    val isSpeaking: Boolean = false,
    val isChangeModeButtonEnabled: Boolean = false,
    val memoryQuizTextAnswers: PersistentList<String> = persistentListOf("", "", ""),

    val showExitDialog: Boolean,
    val showResultDialog: Boolean,
    val quizResult: QuizResult?,
    val correctCount: Int,
) {
    val currentQuiz: Quiz?
        get() = quizzes.getOrNull(currentQuestionIndex)

    val totalSteps: Int
        get() = quizzes.size

    val currentStep: Int
        get() = currentQuestionIndex + 1

    val isContinueButtonEnabled: Boolean
        get() {
            if (isChecking) return false
            val quiz = currentQuiz ?: return false

            return when (quiz) {
                is PersistenceQuiz, is LinguisticQuiz, is SpaceTimeQuiz -> selectedAnswerIndex != null
                is AttentionQuiz -> attentionQuizAnswer.length == quiz.answer.length
                is MemoryQuiz -> false
            }
        }

    companion object {
        val INIT = DailyQuizUiState(
            isLoading = false,
            errorMessage = null,
            quizzes = emptyList(),
            currentQuestionIndex = 0,
            quizResult = null,
            showResultDialog = false,
            showExitDialog = false,
            correctCount = 0,
        )
    }
}
