package com.moa.app.feature.onboarding.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.model.Gender
import com.moa.app.feature.onboarding.signup.state.SignUpPhoneAuthUiState
import com.moa.app.feature.onboarding.signup.state.SignUpProfileUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpSharedViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    private val _signUpUserProfileUiState = MutableStateFlow(SignUpProfileUiState.init)
    val signUpUserProfileUiState = _signUpUserProfileUiState.asStateFlow()

    private val _signUpPhoneAuthUiState = MutableStateFlow(SignUpPhoneAuthUiState.init)
    val signUpPhoneAuthUiState = _signUpPhoneAuthUiState.asStateFlow()

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

    fun requestAuthCode() {
        viewModelScope.launch {
            // TODO: 인증 코드 요청 api 호출
        }
        // TODO: api 요청 성공 실패 분기처리 하기
        _signUpPhoneAuthUiState.update { it.copy(isAuthCodeRequested = true) }
    }

    // 인증 번호 확인 요청
    fun verifyAuthCode() {
        viewModelScope.launch {
            // TODO: 인증 코드 확인 api 호출
        }
        // TODO: 인증 성공 시 회원가입 완료 후 화면 이동
        navigateToComplete()
    }


    fun navigateToBack() = navigator.navigateBack()

    fun navigateToNext() = navigator.navigate(AppRoute.SignUpPhoneAuth)

    private fun navigateToComplete() {
        navigator.navigate(
            route = AppRoute.SignUpComplete,
            options = NavigationOptions(
                popUpTo = AppRoute.SignUp,
                inclusive = true
            )
        )
    }
}
