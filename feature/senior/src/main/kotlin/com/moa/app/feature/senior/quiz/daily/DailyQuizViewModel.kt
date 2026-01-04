package com.moa.app.feature.senior.quiz.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.usecase.FetchDailyQuizzesUseCase
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val navigator: Navigator,
    private val fetchDailyQuizzesUseCase: FetchDailyQuizzesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyQuizUiState())
    val uiState: StateFlow<DailyQuizUiState> = _uiState.asStateFlow()

    init {
        loadDailyQuizzes()
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
                }
            )
        }
    }

    fun navigateToBack() = navigator.navigateBack()
}


data class DailyQuizUiState(
    val isLoading: Boolean = false,
    val quizzes: List<Quiz> = emptyList(),
)
