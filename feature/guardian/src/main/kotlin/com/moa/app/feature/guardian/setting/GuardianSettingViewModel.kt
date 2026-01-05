package com.moa.app.feature.guardian.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.usecase.LogOutUseCase
import com.moa.app.domain.auth.usecase.WithdrawalUseCase
import com.moa.app.domain.user.usecase.FetchUserProfileUseCase
import com.moa.app.feature.guardian.setting.model.GuardianSettingUiState
import com.moa.app.feature.guardian.setting.model.SettingDialogState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GuardianSettingViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val withdrawalUseCase: WithdrawalUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GuardianSettingUiState.INIT)
    val uiState: StateFlow<GuardianSettingUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    fun showLogoutDialog() {
        _uiState.update { it.copy(logoutDialogState = SettingDialogState.Confirm) }
    }

    fun hideLogoutDialog() {
        _uiState.update { it.copy(logoutDialogState = SettingDialogState.None) }
    }

    fun showWithdrawalDialog() {
        _uiState.update { it.copy(withdrawalDialogState = SettingDialogState.Confirm) }
    }

    fun hideWithdrawalDialog() {
        _uiState.update { it.copy(withdrawalDialogState = SettingDialogState.None) }
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchUserProfileUseCase().fold(
                onSuccess = { userProfile ->
                    _uiState.update {
                        it.copy(userName = userProfile.name, isLoading = false)
                    }
                },
                onFailure = { t ->
                    Timber.e("fetchUserProfile: $t")
                    _uiState.update { it.copy(isLoading = false) }
                },
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            logOutUseCase().fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(isLoading = false, logoutDialogState = SettingDialogState.Complete)
                    }
                },
                onFailure = { t ->
                    Timber.e("Logout Failed: $t")
                    _uiState.update {
                        it.copy(isLoading = false, logoutDialogState = SettingDialogState.None)
                    }
                },
            )
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            withdrawalUseCase().fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(isLoading = false, withdrawalDialogState = SettingDialogState.Complete)
                    }
                },
                onFailure = { t ->
                    Timber.e("Withdrawal Failed: $t")
                    _uiState.update {
                        it.copy(isLoading = false, withdrawalDialogState = SettingDialogState.None)
                    }
                },
            )
        }
    }

    fun openPolicyUrl() = navigator.openUrl(POLICY_URL)

    fun openCustomerCenterUrl() = navigator.openUrl(CUSTOMER_CENTER_URL)

    fun navigateToBack() = navigator.navigateBack()

    fun navigateToClear() {
        navigator.navigate(
            route = AppRoute.AuthLanding,
            options = NavigationOptions(
                popUpTo = AppRoute.GuardianSetting,
                inclusive = true,
                clearBackStack = true,
            ),
        )
    }

    companion object {
        private const val POLICY_URL =
            "https://woongaaaa.notion.site/Legal-2b41a839ca3c80bcb357fd347f9535f3"
        private const val CUSTOMER_CENTER_URL = "https://pf.kakao.com/_XxaxbYn/chat"
    }
}
