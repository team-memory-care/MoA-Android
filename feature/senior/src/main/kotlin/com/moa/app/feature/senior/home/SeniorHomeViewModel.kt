package com.moa.app.feature.senior.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.user.usecase.FetchUserProfileUseCase
import com.moa.app.feature.senior.home.model.SeniorHomeUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _uiState: MutableStateFlow<SeniorHomeUiState> = MutableStateFlow(SeniorHomeUiState.INIT)
    val uiState: StateFlow<SeniorHomeUiState> = _uiState.asStateFlow()

    private val _sideEffect: MutableSharedFlow<SeniorHomeSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<SeniorHomeSideEffect> = _sideEffect.asSharedFlow()


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
        navigator.navigate(AppRoute.PersistenceQuiz)
    }

    fun navigateToReport() {
        viewModelScope.launch {
            _sideEffect.emit(SeniorHomeSideEffect.ShowToast("리포트는 아직 준비중인 기능이에요"))
        }
    }


    fun navigateToSetting() {
        navigator.navigate(AppRoute.SeniorSetting)
    }
}

sealed interface SeniorHomeSideEffect {
    data class ShowToast(val message: String) : SeniorHomeSideEffect
}
