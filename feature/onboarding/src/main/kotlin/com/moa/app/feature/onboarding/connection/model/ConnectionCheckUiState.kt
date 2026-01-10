package com.moa.app.feature.onboarding.connection.model

import androidx.compose.runtime.Immutable
import com.moa.app.domain.auth.model.Gender
import com.moa.app.domain.user.model.SeniorProfile

@Immutable
data class ConnectionCheckUiState(
    val isLoading: Boolean,
    val seniorProfile: SeniorProfile?,
) {
    val userInfo: String
        get() = "${seniorProfile?.name ?: "name"} (${seniorProfile?.gender?.koreanDisplayName ?: "gender"})"

    val birthDate: String
        get() = seniorProfile?.birthDate?.replace("-", ".") ?: ""

    companion object Companion {
        val INIT = ConnectionCheckUiState(
            isLoading = false,
            seniorProfile = null,
        )
    }
}

val Gender.koreanDisplayName: String
    get() = when (this) {
        Gender.FEMALE -> "여성"
        Gender.MALE -> "남성"
    }
