package com.moa.app.feature.onboarding.role.model

import com.moa.app.domain.auth.model.UserRole

data class SelectUserUiState(
    val userRole: UserRole?
) {
    val isUserRoleSenior: Boolean
        get() = userRole == UserRole.PARENT

    val isUserRoleGuardian: Boolean
        get() = userRole == UserRole.CHILD

    val isNextEnabled: Boolean
        get() = userRole != null

    companion object {
        val INIT = SelectUserUiState(
            userRole = null
        )
    }
}
