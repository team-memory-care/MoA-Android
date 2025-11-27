package com.moa.app.feature.senior.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.usecase.LogOutUseCase
import com.moa.app.domain.auth.usecase.WithdrawalUseCase
import com.moa.app.domain.user.usecase.FetchUserProfileUseCase
import com.moa.app.feature.senior.setting.model.SeniorSettingUiState
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeniorSettingViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val withdrawalUseCase: WithdrawalUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SeniorSettingUiState> = MutableStateFlow(SeniorSettingUiState.INIT)
    val uiState: StateFlow<SeniorSettingUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    fun showLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun hideLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }

    fun showWithdrawalDialog() {
        _uiState.update { it.copy(showWithdrawalDialog = true) }
    }

    fun hideWithdrawalDialog() {
        _uiState.update { it.copy(showWithdrawalDialog = false) }
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            fetchUserProfileUseCase().fold(
                onSuccess = { userProfile ->
                    _uiState.update {
                        it.copy(userName = userProfile.name, userCode = userProfile.authCode)
                    }
                },
                onFailure = {
                    Log.e("fetchUserProfile", "fetchUserProfile: $it")
                },
            )
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase().fold(
                onSuccess = {
                    navigateToClear()
                },
                onFailure = {
                    Log.e("logOut", "logOut: $it")
                    hideLogoutDialog()
                },
            )
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            withdrawalUseCase().fold(
                onSuccess = {
                    navigateToClear()
                },
                onFailure = {
                    Log.e("withdrawal", "withdrawal: $it")
                    hideWithdrawalDialog()
                },
            )
        }
    }

    fun openPolicyUrl() = navigator.openUrl(POLICY_URL)

    fun openCustomerCenterUrl() = navigator.openUrl(CUSTOMER_CENTER_URL)

    fun navigateToBack() = navigator.navigateBack()

    private fun navigateToClear() {
        navigator.navigate(
            route = AppRoute.AuthLanding,
            options = NavigationOptions(
                popUpTo = AppRoute.SeniorSetting,
                inclusive = true,
                clearBackStack = true,
            )
        )
    }

    companion object {
        private const val POLICY_URL = "https://woongaaaa.notion.site/Legal-2b41a839ca3c80bcb357fd347f9535f3"
        private const val CUSTOMER_CENTER_URL = "https://pf.kakao.com/_XxaxbYn/chat"
    }
}
