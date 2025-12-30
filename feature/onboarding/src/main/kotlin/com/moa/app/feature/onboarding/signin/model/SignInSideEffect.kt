package com.moa.app.feature.onboarding.signin.model

sealed interface SignInSideEffect {
    data object FocusOnAuthCodeField : SignInSideEffect
}
