package com.moa.app.feature.senior.setting.model

data class SeniorSettingUiState(
    val userName: String,
    val userCode: String,
    val showLogoutDialog: Boolean,
    val showWithdrawalDialog: Boolean,
) {
    companion object {
        val INIT = SeniorSettingUiState(
            userName = "",
            userCode = "",
            showLogoutDialog = false,
            showWithdrawalDialog = false,
        )
    }
}
