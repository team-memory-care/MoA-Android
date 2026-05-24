package com.moa.app.feature.senior.quiz.daily

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.AttentionQuiz
import com.moa.app.domain.quiz.model.LinguisticQuiz
import com.moa.app.domain.quiz.model.MemoryQuiz
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.model.SpaceTimeQuiz
import com.moa.app.domain.quiz.model.UserAnswer
import com.moa.app.domain.quiz.usecase.FetchDailyQuizzesUseCase
import com.moa.app.domain.quiz.usecase.UploadQuizScoreUseCase
import com.moa.app.feature.senior.quiz.internal.QUIZ_RESULT_DISPLAY_MS
import com.moa.app.feature.senior.quiz.internal.fetchWithMinDelay
import com.moa.app.feature.senior.quiz.memory.InputMode
import com.moa.app.feature.senior.quiz.memory.MemoryQuizSetState
import com.moa.app.feature.senior.quiz.internal.QuizResult
import com.moa.app.feature.senior.quiz.stt.SttManager
import com.moa.app.feature.senior.quiz.stt.SttState
import com.moa.app.feature.senior.quiz.tts.QuizTextNormalizer
import com.moa.app.feature.senior.quiz.tts.TtsAwareViewModel
import com.moa.app.feature.senior.quiz.tts.TtsManager
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
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
    ttsManager: TtsManager,
    private val sttManager: SttManager,
    private val fetchDailyQuizzesUseCase: FetchDailyQuizzesUseCase,
    private val uploadQuizScoreUseCase: UploadQuizScoreUseCase,
) : TtsAwareViewModel(ttsManager) {

    private val _uiState = MutableStateFlow(DailyQuizUiState.INIT)
    val uiState: StateFlow<DailyQuizUiState> = _uiState.asStateFlow()
    private var memoryImageDisplayJob: Job? = null

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
            fetchWithMinDelay { fetchDailyQuizzesUseCase() }
                .fold(
                    onSuccess = { quizzes ->
                        _uiState.update { it.copy(isLoading = false, quizzes = quizzes) }
                    },
                    onFailure = { t ->
                        Timber.e(t, "loadDailyQuizzes failed")
                        _uiState.update { it.copy(isLoading = false) }
                    },
                )
        }
    }

    fun speakCurrentQuestion() {
        val state = _uiState.value
        val quiz = state.currentQuiz ?: return
        if (state.isLoading || state.showResultDialog) return

        val text = when (quiz) {
            is PersistenceQuiz -> quiz.questionContent
            is LinguisticQuiz -> "아래의 그림은 무엇일까요?"
            is SpaceTimeQuiz -> "겹치는 모양을 찾아주세요!"
            is AttentionQuiz -> QuizTextNormalizer.normalizeExpression(quiz.expression + "=")
            is MemoryQuiz -> {
                when (state.memoryQuizInputMode) {
                    InputMode.VOICE -> "방금 나온 단어를 순서대로 말씀해주세요!"
                    InputMode.TEXT -> "들었던 단어를 밑에 써주세요!"
                }
            }
        }

        ttsManager.speak(text)
    }

    fun displayQuizImages() {
        _uiState.update {
            it.copy(
                memoryQuizState = MemoryQuizSetState.QUESTION_DISPLAY,
                memoryDisplayImageIndex = 0,
            )
        }
        startMemoryImageDisplayTimer()
    }

    private fun onImagesFinished() {
        _uiState.update {
            it.copy(
                memoryQuizState = MemoryQuizSetState.ANSWERING,
                memoryDisplayImageIndex = 0,
            )
        }
    }

    private fun startMemoryImageDisplayTimer() {
        memoryImageDisplayJob?.cancel()
        memoryImageDisplayJob = viewModelScope.launch {
            while (true) {
                delay(MEMORY_IMAGE_DISPLAY_MS)
                val currentState = _uiState.value
                val currentQuiz = currentState.currentQuiz as? MemoryQuiz ?: return@launch
                val nextImageIndex = currentState.memoryDisplayImageIndex + 1

                if (nextImageIndex < currentQuiz.imageUrls.size) {
                    _uiState.update { it.copy(memoryDisplayImageIndex = nextImageIndex) }
                } else {
                    delay(MEMORY_IMAGE_FINISH_DELAY_MS)
                    onImagesFinished()
                    return@launch
                }
            }
        }
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
        _uiState.update { it.copy(memoryQuizInputMode = InputMode.TEXT) }
    }

    fun updateTextAnswer(index: Int, text: String) {
        _uiState.update { state ->
            val updatedAnswers = state.memoryQuizTextAnswers.set(index, text)

            state.copy(memoryQuizTextAnswers = updatedAnswers)
        }
    }

    fun selectAnswer(selectedAnswerIndex: Int) {
        _uiState.update { it.copy(selectedAnswerIndex = selectedAnswerIndex) }
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
        if (ttsManager.isSpeaking) ttsManager.stop()
        if (_uiState.value.isChecking) return

        _uiState.update { state ->
            val currentQuiz = state.currentQuiz ?: return@update state
            val userAnswer = state.toUserAnswer(sttResult)
            val isCorrect = currentQuiz.isAnswerCorrect(userAnswer)

            state.copy(
                isChecking = true,
                showResultDialog = true,
                quizResult = QuizResult(
                    isCorrect = isCorrect,
                    correctAnswer = if (isCorrect) "" else "다른 값",
                ),
                correctCount = if (isCorrect) state.correctCount + 1 else state.correctCount,
            )
        }

        viewModelScope.launch {
            delay(QUIZ_RESULT_DISPLAY_MS)
            goToNextQuestion()
            _uiState.update { it.copy(isChecking = false) }
        }
    }

    private fun goToNextQuestion() {
        memoryImageDisplayJob?.cancel()
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
                    memoryDisplayImageIndex = 0,
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
                category = "TODAY",
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

    private fun DailyQuizUiState.toUserAnswer(sttResult: String?): UserAnswer {
        val quiz = currentQuiz ?: return UserAnswer.Text("")
        return when (quiz) {
            is AttentionQuiz -> UserAnswer.Text(attentionQuizAnswer)
            is PersistenceQuiz, is LinguisticQuiz, is SpaceTimeQuiz -> {
                UserAnswer.Selection(selectedAnswerIndex ?: -1)
            }

            is MemoryQuiz -> {
                if (sttResult != null) UserAnswer.Text(sttResult)
                else UserAnswer.MultipleText(memoryQuizTextAnswers)
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
        memoryImageDisplayJob?.cancel()
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
    val memoryDisplayImageIndex: Int = 0,
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

    val isTextContinueButtonEnabled: Boolean
        get() = memoryQuizTextAnswers.all { it.isNotBlank() }

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

private const val MEMORY_IMAGE_DISPLAY_MS = 1200L
private const val MEMORY_IMAGE_FINISH_DELAY_MS = 500L
