package com.moa.app.feature.guardian.home.model

import com.moa.app.domain.user.model.SeniorProfile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class GuardianHomeUiState(
    val isLoading: Boolean,
    val userName: String,
    val seniorProfiles: ImmutableList<SeniorProfile>,
    val deletable: Boolean,
    val dialogState: DialogState,
) {
    companion object {
        val INIT = GuardianHomeUiState(
            isLoading = false,
            userName = "",
            seniorProfiles = persistentListOf(),
            deletable = false,
            dialogState = DialogState.None,
        )
    }
}
