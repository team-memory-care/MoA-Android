package com.moa.app.feature.onboarding.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.model.Gender
import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.auth.usecase.PhoneAuthCodeUseCase
import com.moa.app.domain.auth.usecase.SignUpUseCase
import com.moa.app.feature.onboarding.signup.model.SignUpPhoneAuthSideEffect
import com.moa.app.feature.onboarding.signup.model.SignUpPhoneAuthUiState
import com.moa.app.feature.onboarding.signup.model.SignUpProfileUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpSharedViewModel @Inject constructor(
    private val navigator: Navigator,
    private val phoneAuthCodeUseCase: PhoneAuthCodeUseCase,
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    private val _signUpUserProfileUiState = MutableStateFlow(SignUpProfileUiState.init)
    val signUpUserProfileUiState = _signUpUserProfileUiState.asStateFlow()

    private val _signUpPhoneAuthUiState = MutableStateFlow(SignUpPhoneAuthUiState.init)
    val signUpPhoneAuthUiState = _signUpPhoneAuthUiState.asStateFlow()

    private val _signUpPhoneAuthSideEffect: MutableSharedFlow<SignUpPhoneAuthSideEffect> = MutableSharedFlow()
    val signUpPhoneAuthSideEffect: SharedFlow<SignUpPhoneAuthSideEffect> = _signUpPhoneAuthSideEffect.asSharedFlow()

    // sign up profile event
    fun updateName(name: String) {
        _signUpUserProfileUiState.update { it.copy(name = name) }
    }

    fun updateYear(year: String) {
        _signUpUserProfileUiState.update { it.copy(year = year) }
    }

    fun updateMonth(month: String) {
        _signUpUserProfileUiState.update { it.copy(month = month) }
    }

    fun updateDay(day: String) {
        _signUpUserProfileUiState.update { it.copy(day = day) }
    }

    fun selectMaleGender() {
        _signUpUserProfileUiState.update { it.copy(gender = Gender.MALE) }
    }

    fun selectFemaleGender() {
        _signUpUserProfileUiState.update { it.copy(gender = Gender.FEMALE) }
    }

    // sign up phone auth event
    fun updatePhoneNumber(phoneNumber: String) {
        _signUpPhoneAuthUiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateAuthCode(authCode: String) {
        _signUpPhoneAuthUiState.update { it.copy(authCode = authCode) }
    }

    fun requestPhoneAuthCode() {
        viewModelScope.launch {
            phoneAuthCodeUseCase(
                phoneNumber = _signUpPhoneAuthUiState.value.phoneNumber,
            ).fold(
                onSuccess = {
                    _signUpPhoneAuthUiState.update { it.copy(isAuthCodeRequested = true) }
                    _signUpPhoneAuthSideEffect.emit(SignUpPhoneAuthSideEffect.FocusOnAuthCodeField)
                },
                onFailure = { error ->
                    _signUpPhoneAuthUiState.update {
                        it.copy(isPhoneNumberError = true, phoneNumberErrorMessage = error.message)
                    }
                    Timber.tag("SignUpSharedViewModel").e("requestAuthCode: $error")
                },
            )
        }
    }

    fun signUp() {
        viewModelScope.launch {
            val gender = _signUpUserProfileUiState.value.gender ?: return@launch
            signUpUseCase(
                userProfile = UserProfile(
                    name = _signUpUserProfileUiState.value.name,
                    birthDate = _signUpUserProfileUiState.value.birthDate,
                    gender = gender,
                    phoneNumber = _signUpPhoneAuthUiState.value.phoneNumber,
                    authCode = _signUpPhoneAuthUiState.value.authCode,
                ),
            ).fold(
                onSuccess = { navigateToComplete() },
                onFailure = { error ->
                    _signUpPhoneAuthUiState.update {
                        it.copy(isAuthCodeError = true, authCodeErrorMessage = error.message)
                    }
                },
            )
        }
    }

    fun navigateToBack() = navigator.navigateBack()

    fun navigateToNext() = navigator.navigate(AppRoute.SignUpPhoneAuth)

    private fun navigateToComplete() {
        navigator.navigate(
            route = AppRoute.SignUpComplete,
            options = NavigationOptions(
                popUpTo = AppRoute.SignUp,
                inclusive = true,
            ),
        )
    }
}
