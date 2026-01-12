package com.moa.app.feature.onboarding.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.model.UserRole
import com.moa.app.domain.auth.usecase.PhoneAuthCodeUseCase
import com.moa.app.domain.auth.usecase.SignInUseCase
import com.moa.app.feature.onboarding.signin.model.SignInSideEffect
import com.moa.app.feature.onboarding.signin.model.SignInUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
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
class SignInViewModel @Inject constructor(
    private val navigator: Navigator,
    private val phoneAuthCodeUseCase: PhoneAuthCodeUseCase,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState.init)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _sideEffect: MutableSharedFlow<SignInSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<SignInSideEffect> = _sideEffect.asSharedFlow()

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateAuthCode(authCode: String) {
        _uiState.update { it.copy(authCode = authCode) }
    }

    fun requestAuthCode() {
        viewModelScope.launch {
            if (_uiState.value.isLoading) return@launch
            _uiState.update { it.copy(isLoading = true) }
            phoneAuthCodeUseCase(
                phoneNumber = _uiState.value.phoneNumber,
                isUserRegistered = true,
            ).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isAuthCodeRequested = true) }
                    _sideEffect.emit(SignInSideEffect.FocusOnAuthCodeField)
                },
                onFailure = { error ->
                    Timber.e("requestAuthCode: $error")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isPhoneNumberError = true,
                            phoneNumberErrorMessage = "인증번호 요청에 실패했습니다.",
                        )
                    }
                },
            )
        }
    }

    fun signIn() {
        viewModelScope.launch {
            if (_uiState.value.isLoading) return@launch
            val phoneNumber = _uiState.value.phoneNumber
            val authCode = _uiState.value.authCode
            signInUseCase(phoneNumber, authCode).fold(
                onSuccess = { userRole -> handleNavigationForRole(userRole) },
                onFailure = { error ->
                    Timber.e("signIn: $error")
                    _uiState.update {
                        it.copy(
                            isAuthCodeError = true,
                            authCodeErrorMessage = "인증번호가 일치하지 않아요.\n다시 확인해주세요.",
                        )
                    }
                },
            )
        }
    }

    private fun handleNavigationForRole(userRole: UserRole) {
        when (userRole) {
            UserRole.PARENT -> navigateToRoute(AppRoute.SeniorHome)
            UserRole.CHILD -> navigateToRoute(AppRoute.GuardianHome)
            UserRole.PENDING -> navigateToRoute(AppRoute.SelectUserRole)
            else -> {}
        }
    }

    private fun navigateToRoute(route: AppRoute) {
        navigator.navigate(
            route = route,
            options = NavigationOptions(
                popUpTo = AppRoute.SignIn,
                inclusive = true,
            ),
        )
    }

    fun navigateToBack() = navigator.navigateBack()
}
