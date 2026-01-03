package com.moa.app.feature.onboarding.connection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.moa.app.domain.auth.model.UserRole
import com.moa.app.domain.auth.usecase.SetParentRoleUseCase
import com.moa.app.domain.user.usecase.ValidateParentCodeUseCase
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
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class UserConnectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val setParentRoleUseCase: SetParentRoleUseCase,
    private val validateParentCodeUseCase: ValidateParentCodeUseCase,
) : ViewModel() {

    private val userRole = savedStateHandle.toRoute<AppRoute.UserConnection>().userRole

    private val _uiState = MutableStateFlow(UserConnectionUiState.INIT)
    val uiState: StateFlow<UserConnectionUiState> = _uiState.asStateFlow()

    init {
        setUserRole(userRole)
    }

    private fun setUserRole(role: String) {
        val userRole = UserRole.fromString(role)
        _uiState.update { it.copy(userRole = userRole) }

        if (userRole == UserRole.PARENT) setParentRole()
    }

    private fun setParentRole() {
        viewModelScope.launch {
            setParentRoleUseCase().fold(
                onSuccess = { userCode ->
                    _uiState.update { it.copy(userCode = userCode) }
                },
                onFailure = {
                    Timber.e("setParentRole: $it")
                },
            )
        }
    }

    fun updateUserCode(code: String) {
        _uiState.update { it.copy(userCode = code) }
    }

    private fun validateUserCode(userCode: String) {
        viewModelScope.launch {
            validateParentCodeUseCase(userCode).fold(
                onSuccess = { userId -> navigateToConnectionCheck(userId) },
                onFailure = { t ->
                    Timber.e("validateUserCode: $t")
                    _uiState.update {
                        it.copy(errorMessage = "* 유효하지 않은 회원코드예요. 다시 확인해주세요.")
                    }
                },
            )
        }
    }

    fun navigateToNext() {
        val state = _uiState.value
        if (state.isUserSenior) navigateToSeniorHome() else validateUserCode(state.userCode)
    }

    private fun navigateToSeniorHome() {
        navigator.navigate(
            route = AppRoute.SeniorHome,
            options = NavigationOptions(
                popUpTo = AppRoute.UserConnection("CHILD"),
                inclusive = true,
                clearBackStack = true,
            ),
        )
    }

    private fun navigateToConnectionCheck(userId: Long) {
        navigator.navigate(route = AppRoute.ConnectionCheck(userId))
    }

    fun navigateToBack() = navigator.navigateBack()
}
