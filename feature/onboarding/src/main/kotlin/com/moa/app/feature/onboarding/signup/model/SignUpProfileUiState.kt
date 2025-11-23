package com.moa.app.feature.onboarding.signup.model

import com.moa.app.domain.auth.model.Gender

data class SignUpProfileUiState(
    val name: String,
    val year: String,
    val month: String,
    val day: String,
    val gender: Gender?,
) {
    val isNextEnabled: Boolean
        get() = name.isNotEmpty() && year.isNotEmpty() && month.isNotEmpty() && day.isNotEmpty() && gender != null

    val isGenderMale: Boolean
        get() = gender == Gender.MALE

    val isGenderFemale: Boolean
        get() = gender == Gender.FEMALE


    companion object {
        val init = SignUpProfileUiState(
            name = "",
            year = "",
            month = "",
            day = "",
            gender = null,
        )
    }
}
