package com.moa.app.feature.senior.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.user.usecase.FetchUserProfileUseCase
import com.moa.app.feature.senior.home.model.SeniorHomeUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SeniorHomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeniorHomeUiState.INIT)
    val uiState: StateFlow<SeniorHomeUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            fetchUserProfileUseCase().fold(
                onSuccess = { userProfile ->
                    _uiState.update { it.copy(userName = userProfile.name) }
                },
                onFailure = {
                    Timber.tag("fetchUserProfile").e("fetchUserProfile: $it")
                },
            )
        }
    }

    fun navigateToQuizCategory() {
        navigator.navigate(AppRoute.QuizCategory)
    }

    fun navigateToDailyQuiz() {
        navigator.navigate(AppRoute.DailyQuiz)
    }

    fun navigateToReport() {
        navigator.navigate(AppRoute.Report(null))
    }

    fun navigateToSetting() {
        navigator.navigate(AppRoute.SeniorSetting)
    }
}
