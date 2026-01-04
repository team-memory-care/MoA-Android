package com.moa.app.feature.senior.quiz.daily

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DailyQuizViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyQuizUiState())
    val uiState: StateFlow<DailyQuizUiState> = _uiState.asStateFlow()
}


data class DailyQuizUiState(
    val isLoading: Boolean = true,
)
