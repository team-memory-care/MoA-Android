package com.moa.app.feature.onboarding.signup.state

data class SignUpProfileUiState(
    val name: String,
    val year: String,
    val month: String,
    val day: String,
    val gender: String,
) {
    val isNextEnabled: Boolean
        get() = name.isNotEmpty() && year.isNotEmpty() && month.isNotEmpty() && day.isNotEmpty() && gender.isNotEmpty()

    companion object {
        val init = SignUpProfileUiState(
            name = "",
            year = "",
            month = "",
            day = "",
            gender = "",
        )
    }
}
