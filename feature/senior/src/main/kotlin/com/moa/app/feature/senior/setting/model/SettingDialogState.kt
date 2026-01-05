package com.moa.app.feature.senior.setting.model

import androidx.compose.runtime.Immutable

@Immutable
interface SettingDialogState {
    data object None : SettingDialogState
    data object Confirm : SettingDialogState
    data object Complete : SettingDialogState
}
