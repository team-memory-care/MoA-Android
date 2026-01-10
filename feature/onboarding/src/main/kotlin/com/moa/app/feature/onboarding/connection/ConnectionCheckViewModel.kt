package com.moa.app.feature.onboarding.connection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.moa.app.domain.user.usecase.ConnectToSeniorUseCase
import com.moa.app.domain.user.usecase.FetchSeniorProfilesUseCase
import com.moa.app.feature.onboarding.connection.model.ConnectionCheckUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
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
class ConnectionCheckViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val fetchSeniorProfilesUseCase: FetchSeniorProfilesUseCase,
    private val connectToSeniorUseCase: ConnectToSeniorUseCase,
) : ViewModel() {

    private val userId = savedStateHandle.toRoute<AppRoute.ConnectionCheck>().userId

    private val _uiState = MutableStateFlow(ConnectionCheckUiState.INIT)
    val uiState: StateFlow<ConnectionCheckUiState> = _uiState.asStateFlow()

    init {
        loadSeniorProfile(userId)
    }

    private fun loadSeniorProfile(userId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchSeniorProfilesUseCase(userId).fold(
                onSuccess = { seniorProfile ->
                    _uiState.update { it.copy(isLoading = false, seniorProfile = seniorProfile) }
                },
                onFailure = { t ->
                    _uiState.update { it.copy(isLoading = false) }
                    Timber.e("loadSeniorProfile: $t")
                },
            )
        }
    }

    fun connectToSenior() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            connectToSeniorUseCase(userId).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    navigateToGuardianHome()
                },
                onFailure = { t ->
                    _uiState.update { it.copy(isLoading = false) }
                    Timber.e("connectToSenior: $t")
                }
            )

        }
    }

    private fun navigateToGuardianHome() {
        navigator.navigate(
            route = AppRoute.GuardianHome,
            options = NavigationOptions(
                popUpTo = AppRoute.ConnectionCheck(userId),
                inclusive = true,
                clearBackStack = true,
            )
        )
    }

    fun navigateToBack() = navigator.navigateBack()
}

