package com.moa.app.feature.senior.setting.model

data class SeniorSettingUiState(
    val isLoading: Boolean,
    val userName: String,
    val userCode: String,
    val withdrawalDialogState: SettingDialogState,
    val logoutDialogState: SettingDialogState,
) {
    companion object {
        val INIT = SeniorSettingUiState(
            isLoading = false,
            userName = "",
            userCode = "",
            withdrawalDialogState = SettingDialogState.None,
            logoutDialogState = SettingDialogState.None,
        )
    }
}
