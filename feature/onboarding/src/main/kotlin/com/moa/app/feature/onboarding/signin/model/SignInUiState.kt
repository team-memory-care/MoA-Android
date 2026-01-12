package com.moa.app.feature.onboarding.signin.model

data class SignInUiState(
    val isLoading: Boolean,
    val phoneNumber: String,
    val authCode: String,
    val isAuthCodeRequested: Boolean,
    val isPhoneNumberError: Boolean,
    val phoneNumberErrorMessage: String?,
    val isAuthCodeError: Boolean,
    val authCodeErrorMessage: String?,
) {
    companion object {
        val init = SignInUiState(
            isLoading = false,
            phoneNumber = "",
            authCode = "",
            isAuthCodeRequested = false,
            isPhoneNumberError = false,
            phoneNumberErrorMessage = null,
            isAuthCodeError = false,
            authCodeErrorMessage = null,
        )
    }
}
