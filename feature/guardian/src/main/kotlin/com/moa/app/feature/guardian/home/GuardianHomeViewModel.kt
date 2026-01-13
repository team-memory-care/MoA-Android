package com.moa.app.feature.guardian.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.user.usecase.DeleteSeniorProfileUseCase
import com.moa.app.domain.user.usecase.FetchSeniorProfilesUseCase
import com.moa.app.domain.user.usecase.FetchUserProfileUseCase
import com.moa.app.feature.guardian.home.model.DialogState
import com.moa.app.feature.guardian.home.model.GuardianHomeUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GuardianHomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val fetchSeniorProfilesUseCase: FetchSeniorProfilesUseCase,
    private val deleteSeniorProfileUseCase: DeleteSeniorProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GuardianHomeUiState.INIT)
    val uiState: StateFlow<GuardianHomeUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
        loadSeniorProfiles()
    }

    fun showDeleteDialog(userId: Long) {
        val profile = _uiState.value.seniorProfiles.find { it.id == userId } ?: return
        _uiState.update { it.copy(dialogState = DialogState.DeleteConfirm(profile)) }
    }

    fun hideDialog() {
        _uiState.update { it.copy(dialogState = DialogState.None) }
    }

    fun updateDeletable() {
        _uiState.update { it.copy(deletable = !_uiState.value.deletable) }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            val profile = (_uiState.value.dialogState as? DialogState.DeleteConfirm)?.profile ?: return@launch

            _uiState.update { it.copy(isLoading = true) }
            deleteSeniorProfileUseCase(profile.id).fold(
                onSuccess = {
                    loadSeniorProfiles()
                    _uiState.update {
                        it.copy(isLoading = false, dialogState = DialogState.DeleteComplete)
                    }
                },
                onFailure = { t ->
                    _uiState.update { it.copy(isLoading = false, dialogState = DialogState.None) }
                    Timber.e("deleteProfile: $t")
                },
            )
        }
    }

    private fun loadSeniorProfiles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchSeniorProfilesUseCase().fold(
                onSuccess = { seniorProfiles ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            seniorProfiles = seniorProfiles.toImmutableList(),
                        )
                    }
                },
                onFailure = { t ->
                    _uiState.update { it.copy(isLoading = false) }
                    Timber.e("loadSeniorProfiles: $t")
                },
            )
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchUserProfileUseCase().fold(
                onSuccess = { userProfile ->
                    _uiState.update { it.copy(isLoading = false, userName = userProfile.name) }
                },
                onFailure = { t ->
                    _uiState.update { it.copy(isLoading = false) }
                    Timber.e("loadUserProfile: $t")
                },
            )
        }
    }

    fun navigateToUserConnection() {
        navigator.navigate(
            route = AppRoute.UserConnection("CHILD"),
            options = NavigationOptions(launchSingleTop = true)
        )
    }

    fun navigateToReport(profileId: Long) {
        navigator.navigate(
            route = AppRoute.Report(profileId),
            options = NavigationOptions(launchSingleTop = true)
        )
    }

    fun navigateToAlert() {
        navigator.navigate(
            route = AppRoute.GuardianAlert,
            options = NavigationOptions(launchSingleTop = true)
        )
    }

    fun navigateToSetting() {
        navigator.navigate(
            route = AppRoute.GuardianSetting,
            options = NavigationOptions(launchSingleTop = true)
        )
    }
}

