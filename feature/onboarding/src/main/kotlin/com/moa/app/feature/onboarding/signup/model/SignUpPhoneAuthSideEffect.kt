package com.moa.app.feature.onboarding.signup.model

sealed interface SignUpPhoneAuthSideEffect {
    data object FocusOnAuthCodeField : SignUpPhoneAuthSideEffect
}
