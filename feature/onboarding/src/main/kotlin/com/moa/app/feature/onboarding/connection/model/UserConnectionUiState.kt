package com.moa.app.feature.onboarding.connection.model

import com.moa.app.domain.auth.model.UserRole

data class UserConnectionUiState(
    val userCode: String,
    val userRole: UserRole?
) {
    val isUserSenior: Boolean
        get() = userRole == UserRole.PARENT

    val getTitle: String
        get() = if (isUserSenior) SENIOR_TITLE else GUARDIAN_TITLE

    val isNextEnabled: Boolean
        get() = if (isUserSenior) true else userCode.length == GUARDIAN_CODE_LENGTH


    companion object {
        val INIT = UserConnectionUiState(
            userCode = "",
            userRole = null
        )

        private const val SENIOR_TITLE = "가족이나 보호자에게\n회원코드를 보내주세요"
        private const val GUARDIAN_TITLE = "상대방의 회원코드를\n입력해 연결해주세요"
        private const val GUARDIAN_CODE_LENGTH = 4
    }
}
