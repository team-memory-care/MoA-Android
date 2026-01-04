package com.moa.app.feature.guardian.setting.model

import androidx.compose.runtime.Immutable

@Immutable
data class GuardianSettingUiState(
    val isLoading: Boolean,
    val userName: String,
    val withdrawalDialogState: SettingDialogState,
    val logoutDialogState: SettingDialogState,
) {
    companion object {
        val INIT = GuardianSettingUiState(
            isLoading = false,
            userName = "",
            withdrawalDialogState = SettingDialogState.None,
            logoutDialogState = SettingDialogState.None,
        )
    }
}
