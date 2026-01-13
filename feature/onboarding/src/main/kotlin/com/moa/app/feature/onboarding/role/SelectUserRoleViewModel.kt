package com.moa.app.feature.onboarding.role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.model.UserRole
import com.moa.app.feature.onboarding.role.model.SelectUserUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SelectUserRoleViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectUserUiState.INIT)
    val uiState: StateFlow<SelectUserUiState> = _uiState.asStateFlow()

    fun updateUserRole(role: UserRole) {
        _uiState.update { it.copy(userRole = role) }
    }

    fun navigateToUserConnection() {
        val role = _uiState.value.userRole ?: return

        navigator.navigate(
            route = AppRoute.UserConnection(role.toString()),
            options = NavigationOptions(
                launchSingleTop = true
            )
        )
    }

    fun navigateToBack() = navigator.navigateBack()
}
