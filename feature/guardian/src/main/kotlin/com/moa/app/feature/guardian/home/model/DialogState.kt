package com.moa.app.feature.guardian.home.model

import androidx.compose.runtime.Immutable
import com.moa.app.domain.user.model.SeniorProfile

@Immutable
sealed interface DialogState {
    data object None : DialogState
    data class DeleteConfirm(val profile: SeniorProfile) : DialogState
    data object DeleteComplete : DialogState
}
