package com.moa.app.feature.onboarding.role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.model.UserRole
import com.moa.app.feature.onboarding.role.model.SelectUserSideEffect
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

    private val _uiState: MutableStateFlow<SelectUserUiState> = MutableStateFlow(SelectUserUiState.INIT)
    val uiState: StateFlow<SelectUserUiState> = _uiState.asStateFlow()

    private val _sideEffect: MutableSharedFlow<SelectUserSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<SelectUserSideEffect> = _sideEffect.asSharedFlow()

    fun updateUserRole(role: UserRole) {
        _uiState.update { it.copy(userRole = role) }
    }

    fun navigateToBack() = navigator.navigateBack()

    fun navigateToUserConnection() {
        val role = _uiState.value.userRole ?: return

        if (role == UserRole.CHILD) {
            viewModelScope.launch {
                _sideEffect.emit(
                    SelectUserSideEffect.ShowToast("보호자 역할은 아직 지원하지 않습니다")
                )
            }
            return
        }

        navigator.navigate(
            route = AppRoute.UserConnection(role.toString()),
            options = NavigationOptions(
                launchSingleTop = true
            )
        )
    }
}
