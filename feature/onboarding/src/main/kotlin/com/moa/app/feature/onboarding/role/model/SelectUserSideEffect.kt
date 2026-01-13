package com.moa.app.feature.onboarding.role.model

sealed interface SelectUserSideEffect {
    data class ShowToast(val message: String) : SelectUserSideEffect
}
