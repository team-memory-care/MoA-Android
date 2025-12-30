package com.moa.app.feature.onboarding.signup.model

data class SignUpPhoneAuthUiState(
    val phoneNumber: String,
    val authCode: String,
    val isAuthCodeRequested: Boolean,
    val isPhoneNumberError: Boolean,
    val phoneNumberErrorMessage: String?,
    val isAuthCodeError: Boolean,
    val authCodeErrorMessage: String?,
) {
    companion object {
        val init = SignUpPhoneAuthUiState(
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
