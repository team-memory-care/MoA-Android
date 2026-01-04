package com.moa.app.feature.guardian.setting.model

import androidx.compose.runtime.Immutable

@Immutable
interface SettingDialogState {
    data object None : SettingDialogState
    data object Confirm : SettingDialogState
    data object Complete : SettingDialogState
}
