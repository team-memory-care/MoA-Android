package com.moa.app.feature.onboarding.connection.model

import com.moa.app.domain.auth.model.UserRole

data class UserConnectionUiState(
    val userCode: String,
    val userRole: UserRole?,
    val errorMessage: String?
) {
    val isUserSenior: Boolean
        get() = userRole == UserRole.PARENT

    val setTitle: String
        get() = if (isUserSenior) SENIOR_TITLE else GUARDIAN_TITLE

    val setButtonTitle: String
        get() = if (isUserSenior) NEXT_ENABLED_TITLE else NEXT_TITLE

    val isNextEnabled: Boolean
        get() = if (isUserSenior) true else userCode.length == GUARDIAN_CODE_LENGTH


    companion object {
        val INIT = UserConnectionUiState(
            userCode = "",
            userRole = null,
            errorMessage = null
        )

        private const val SENIOR_TITLE = "가족이나 보호자에게\n회원코드를 보내주세요"
        private const val GUARDIAN_TITLE = "상대방의 회원코드를\n입력해 연결해주세요"
        private const val NEXT_TITLE = "다음"
        private const val NEXT_ENABLED_TITLE = "넘어가기"
        private const val GUARDIAN_CODE_LENGTH = 4
    }
}
