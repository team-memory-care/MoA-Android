package com.moa.app.feature.senior.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.feature.senior.setting.model.SeniorSettingUiState
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
) : ViewModel() {

    private val _uiState: MutableStateFlow<SeniorSettingUiState> = MutableStateFlow(SeniorSettingUiState.INIT)
    val uiState: StateFlow<SeniorSettingUiState> = _uiState.asStateFlow()

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

    fun fetchUserProfile() {
        viewModelScope.launch {

        }
    }

    fun logOut() {
        hideLogoutDialog()
    }

    fun withdrawal() {
        hideWithdrawalDialog()
    }

    fun openPolicyUrl() = navigator.openUrl(POLICY_URL)

    fun openCustomerCenterUrl() = navigator.openUrl(CUSTOMER_CENTER_URL)

    fun navigateToBack() = navigator.navigateBack()

    companion object {
        private const val POLICY_URL = "https://woongaaaa.notion.site/Legal-2b41a839ca3c80bcb357fd347f9535f3"
        private const val CUSTOMER_CENTER_URL = "https://pf.kakao.com/_XxaxbYn/chat"
    }
}
