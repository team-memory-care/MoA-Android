package com.moa.app.feature.onboarding.connection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.moa.app.domain.auth.model.UserRole
import com.moa.app.feature.onboarding.connection.model.UserConnectionUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class UserConnectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator
) : ViewModel() {

    private val userRole = savedStateHandle.toRoute<AppRoute.UserConnection>().userRole

    private val _uiState: MutableStateFlow<UserConnectionUiState> = MutableStateFlow(UserConnectionUiState.INIT)
    val uiState: StateFlow<UserConnectionUiState> = _uiState.asStateFlow()

    init {
        setUserRole(userRole)
    }

    private fun setUserRole(role: String) {
        val userRole = UserRole.fromString(role)
        _uiState.update { it.copy(userRole = userRole) }
    }

    fun updateUserCode(code: String) {
        _uiState.update { it.copy(userCode = code) }
    }

    private fun navigateToSeniorHome() {
        navigator.navigate(
            route = AppRoute.SeniorHome,
            options = NavigationOptions(
                popUpTo = AppRoute.UserConnection(userRole),
                inclusive = true,
                clearBackStack = true
            )
        )
    }

    fun navigateToNext() {
        if (_uiState.value.isUserSenior) {
            navigateToSeniorHome()
        } else {
            // TODO
        }
    }

    fun navigateToBack() = navigator.navigateBack()
}
