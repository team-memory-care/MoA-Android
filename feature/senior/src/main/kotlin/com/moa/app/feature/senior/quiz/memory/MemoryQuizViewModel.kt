package com.moa.app.feature.senior.quiz.memory

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Immutable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.MemoryQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.model.QuizResult
import com.moa.app.feature.senior.quiz.stt.SttManager
import com.moa.app.feature.senior.quiz.stt.SttState
import com.moa.app.feature.senior.quiz.tts.TtsManager
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
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
class MemoryQuizViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val navigator: Navigator,
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
    private val ttsManager: TtsManager,
    private val sttManager: SttManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemoryQuizUiState.INIT)
    val uiState: StateFlow<MemoryQuizUiState> = _uiState.asStateFlow()

    init {
        observeSttState()
        loadMemoryQuizzes()

        when (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)) {
            PackageManager.PERMISSION_GRANTED -> {}
            else -> switchToTextMode()
        }
    }

    fun speakCurrentQuestion() {
        val currentState = _uiState.value
        val instruction = when (currentState.inputMode) {
            InputMode.VOICE -> "방금 나온 단어를 순서대로 말씀해주세요!"
            InputMode.TEXT -> "들었던 단어를 밑에 써주세요!"
        }

        ttsManager.speak(instruction)
    }

    fun displayQuizImages() {
        _uiState.update { it.copy(quizState = MemoryQuizSetState.QUESTION_DISPLAY) }
    }

    fun onImagesFinished() {
        _uiState.update { it.copy(quizState = MemoryQuizSetState.ANSWERING) }
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

    fun startListening() {
        if (ttsManager.isSpeaking) ttsManager.stop()
        sttManager.startListening()
    }

    fun displayChangeModeButton() {
        _uiState.update { it.copy(isChangeModeButtonEnabled = true) }
    }

    fun switchToTextMode() {
        _uiState.update { it.copy(inputMode = InputMode.TEXT) }
    }

    fun updateTextAnswer(index: Int, text: String) {
        _uiState.update { state ->
            val updatedAnswers = state.userTextAnswers.set(index, text)

            state.copy(userTextAnswers = updatedAnswers)
        }
    }

    private fun checkAnswer(answer: String) {
        _uiState.update {
            val quiz = it.currentQuiz ?: return@update it
            val isCorrect = quiz.isAnswerCorrect(answer)
            val correctAnswer = if (isCorrect) "" else "다른 값"

            it.copy(
                showResultDialog = true,
                quizResult = QuizResult(isCorrect = isCorrect, correctAnswer = correctAnswer),
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
            )
        }

        viewModelScope.launch {
            delay(2000L)
            goToNextQuestion()
        }
    }

    fun checkTextAnswer() {
        if (ttsManager.isSpeaking) ttsManager.stop()
        _uiState.update {
            val quiz = it.currentQuiz ?: return@update it
            val answer = it.userTextAnswers
            val isCorrect = quiz.isAnswerCorrect(answer)
            val correctAnswer = if (isCorrect) "" else "다른 값"

            it.copy(
                showResultDialog = true,
                quizResult = QuizResult(isCorrect = isCorrect, correctAnswer = correctAnswer),
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
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
                    showResultDialog = false,
                    quizResult = null,
                    quizState = MemoryQuizSetState.WAITING_TO_START,
                    userTextAnswers = persistentListOf("", "", ""),
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
                type = QuizCategory.MEMORY,
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

    private fun loadMemoryQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val minLoadingTime = async { delay(2000L) }
            val quizzesDeferred = async { fetchQuizUseCase(QuizCategory.MEMORY) }
            awaitAll(minLoadingTime, quizzesDeferred)
            quizzesDeferred.await().fold(
                onSuccess = { quizzes ->
                    val memoryQuizzes = quizzes.filterIsInstance<MemoryQuiz>()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quizzes = memoryQuizzes.toImmutableList(),
                        )
                    }
                },
                onFailure = { t ->
                    Timber.e(t, "loadMemoryQuizzes failed")
                    _uiState.update { it.copy(isLoading = false) }
                },
            )
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
        ttsManager.destroy()
    }
}

@Immutable
data class MemoryQuizUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val quizzes: ImmutableList<MemoryQuiz>,
    val quizState: MemoryQuizSetState,
    val currentQuestionIndex: Int,
    val quizResult: QuizResult?,
    val showResultDialog: Boolean,
    val showExitDialog: Boolean,
    val correctCount: Int,
    val inputMode: InputMode,
    val isSpeaking: Boolean,
    val isChangeModeButtonEnabled: Boolean,
    val userTextAnswers: PersistentList<String>,
) {
    val currentQuiz: MemoryQuiz?
        get() = quizzes.getOrNull(currentQuestionIndex)

    val totalSteps: Int
        get() = quizzes.size

    val currentStep: Int
        get() = currentQuestionIndex + 1

    val isTextContinueButtonEnabled: Boolean
        get() = userTextAnswers.all { it.isNotBlank() }

    companion object {
        val INIT = MemoryQuizUiState(
            isLoading = false,
            errorMessage = null,
            quizzes = persistentListOf(),
            quizState = MemoryQuizSetState.WAITING_TO_START,
            currentQuestionIndex = 0,
            quizResult = null,
            showResultDialog = false,
            showExitDialog = false,
            correctCount = 0,
            inputMode = InputMode.VOICE,
            isSpeaking = false,
            isChangeModeButtonEnabled = false,
            userTextAnswers = persistentListOf("", "", ""),
        )
    }
}

enum class MemoryQuizSetState { WAITING_TO_START, QUESTION_DISPLAY, ANSWERING }
enum class InputMode { TEXT, VOICE }
