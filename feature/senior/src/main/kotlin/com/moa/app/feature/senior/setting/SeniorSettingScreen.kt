package com.moa.app.feature.senior.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.product.dialog.MaAlertDialog
import com.moa.app.designsystem.component.product.dialog.MaConfirmDialog
import com.moa.app.designsystem.component.product.setting.ProfileSection
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.designsystem.component.product.setting.OtherSection
import com.moa.app.feature.senior.setting.model.SeniorSettingUiState
import com.moa.app.feature.senior.setting.model.SettingDialogState

@Composable
fun SeniorSettingScreen(
    viewModel: SeniorSettingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SeniorSettingContent(
        uiState = uiState,
        onCustomerCenterClick = viewModel::openCustomerCenterUrl,
        onPolicyClick = viewModel::openPolicyUrl,
        onLogOutClick = viewModel::showLogoutDialog,
        onWithdrawalClick = viewModel::showWithdrawalDialog,
        onBackClick = viewModel::navigateToBack
    )

    when (uiState.logoutDialogState) {
        is SettingDialogState.None -> Unit
        is SettingDialogState.Confirm -> {
            MaAlertDialog(
                title = "로그아웃 하시겠습니까?",
                confirmButtonText = "로그아웃 하기",
                dismissButtonText = "취소",
                onConfirm = viewModel::logout,
                onDismiss = viewModel::hideLogoutDialog,
            )
        }

        is SettingDialogState.Complete -> {
            MaConfirmDialog(
                title = "로그아웃 완료되었습니다",
                confirmButtonText = "확인",
                onConfirm = viewModel::navigateToClear,
                onDialogDismissRequest = {},
            )
        }
    }

    when (uiState.withdrawalDialogState) {
        is SettingDialogState.None -> Unit
        is SettingDialogState.Confirm -> {
            MaAlertDialog(
                title = "회원탈퇴 하시겠습니까?",
                confirmButtonText = "탈퇴하기",
                dismissButtonText = "취소",
                onConfirm = viewModel::withdrawal,
                onDismiss = viewModel::hideWithdrawalDialog,
            )
        }

        is SettingDialogState.Complete -> {
            MaConfirmDialog(
                title = "회원탈퇴 완료되었습니다",
                confirmButtonText = "확인",
                onConfirm = viewModel::navigateToClear,
                onDialogDismissRequest = {},
            )
        }
    }
}

@Composable
private fun SeniorSettingContent(
    uiState: SeniorSettingUiState,
    onCustomerCenterClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onWithdrawalClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaTopAppBar(title = "설정", onBackClick = onBackClick)

        ProfileSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            name = uiState.userName,
            code = uiState.userCode,
            isCodeEnabled = true,
            onCopyClick = {},
        )

        OtherSection(
            onCustomerCenterClick = onCustomerCenterClick,
            onPolicyClick = onPolicyClick,
            onLogOutClick = onLogOutClick,
            onWithdrawalClick = onWithdrawalClick,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "앱 버전 정보",
                color = MoaTheme.colors.coolGray90,
                style = MoaTheme.typography.body1Medium,
            )
            Text(
                text = "v.1.0.0",
                color = MoaTheme.colors.coolGray90,
                style = MoaTheme.typography.body1Medium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SeniorSettingContent(
        uiState = SeniorSettingUiState.INIT,
        onCustomerCenterClick = {},
        onPolicyClick = {},
        onLogOutClick = {},
        onWithdrawalClick = {},
        onBackClick = {}
    )
}
