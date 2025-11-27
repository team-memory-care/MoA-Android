package com.moa.app.feature.senior.quiz.persistence

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.usecase.FetchPersistenceQuizUseCase
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersistenceQuizViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchPersistenceQuizUseCase: FetchPersistenceQuizUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<PersistenceQuizUiState> = MutableStateFlow(PersistenceQuizUiState.Loading)
    val uiState: StateFlow<PersistenceQuizUiState> = _uiState.asStateFlow()

    init {
        loadQuizzes()
    }

    private fun loadQuizzes() {
        viewModelScope.launch {
            val minLoadingTime = async { delay(2000L) }
            val quizzesDeferred = async {
                fetchPersistenceQuizUseCase(QuizCategory.PERSISTENCE)
            }
            awaitAll(minLoadingTime, quizzesDeferred)
            quizzesDeferred.await().fold(
                onSuccess = { quizzes ->
                    _uiState.update {
                        PersistenceQuizUiState.Success(quizzes = quizzes.toImmutableList())
                    }
                },
                onFailure = { t ->
                    _uiState.update {
                        PersistenceQuizUiState.Error(message = "퀴즈가 존재하지 않습니다.")
                    }
                    Log.e("PersistenceQuizViewModel", "loadQuizzes: $t")
                },
            )
        }
    }

    fun selectAnswer(selectedAnswerIndex: Int) {
        _uiState.update {
            if (it is PersistenceQuizUiState.Success && !it.showResultDialog) {
                it.copy(selectedAnswerIndex = selectedAnswerIndex)
            } else {
                it
            }
        }
    }

    fun checkAnswer() {
        _uiState.update {
            if (it !is PersistenceQuizUiState.Success || it.showResultDialog) return@update it
            val selectedAnswerIndex = it.selectedAnswerIndex ?: return@update it
            val currentQuiz = it.quizzes.getOrNull(it.currentQuestionIndex) ?: return@update it
            val isCorrect = currentQuiz.getCurrentAnswerIndex(selectedAnswerIndex)

            it.copy(
                showResultDialog = true,
                dialogResult = DialogResult(
                    isCorrect = isCorrect,
                    correctAnswer = if (isCorrect) "" else currentQuiz.answer,
                ),
            )
        }

        viewModelScope.launch {
            delay(DIALOG_DURATION_MS)
            goToNextQuestion()
        }
    }

    private fun goToNextQuestion() {
        val currentState = _uiState.value as? PersistenceQuizUiState.Success ?: return
        val nextQuestionIndex = currentState.currentQuestionIndex + 1
        val isLastQuestion = nextQuestionIndex >= currentState.quizzes.size

        if (isLastQuestion) {
            _uiState.update {
                if (it is PersistenceQuizUiState.Success) {
                    it.copy(showResultDialog = false, dialogResult = null)
                } else {
                    it
                }
            }

            viewModelScope.launch {
                delay(DIALOG_DISMISS_ANIMATION_MS)
                exitQuiz()
            }
        } else {
            _uiState.update {
                if (it is PersistenceQuizUiState.Success) {
                    it.copy(
                        currentQuestionIndex = nextQuestionIndex,
                        selectedAnswerIndex = null,
                        showResultDialog = false,
                        dialogResult = null,
                    )
                } else {
                    it
                }
            }
        }
    }

    fun onBackClick() {
        _uiState.update {
            if (it is PersistenceQuizUiState.Success && !it.exitDialog) {
                it.copy(exitDialog = true)
            } else {
                it
            }
        }
    }

    fun onHideExitDialog() {
        _uiState.update {
            if (it is PersistenceQuizUiState.Success && it.exitDialog) {
                it.copy(exitDialog = false)
            } else {
                it
            }
        }
    }

    fun exitQuiz() = navigator.navigateBack()

    companion object {
        private const val DIALOG_DURATION_MS = 2000L
        private const val DIALOG_DISMISS_ANIMATION_MS = 200L
    }
}

@Immutable
sealed interface PersistenceQuizUiState {
    data object Loading : PersistenceQuizUiState
    data class Error(val message: String) : PersistenceQuizUiState
    data class Success(
        val quizzes: ImmutableList<PersistenceQuiz>,
        val currentQuestionIndex: Int = 0,
        val selectedAnswerIndex: Int? = null,
        val showResultDialog: Boolean = false,
        val dialogResult: DialogResult? = null,
        val exitDialog: Boolean = false
    ) : PersistenceQuizUiState {
        val currentStep: Int
            get() = currentQuestionIndex + 1

        val totalSteps: Int
            get() = quizzes.size

        val isContinueButtonEnabled: Boolean
            get() = selectedAnswerIndex != null && !showResultDialog
    }
}

data class DialogResult(val isCorrect: Boolean, val correctAnswer: String)
