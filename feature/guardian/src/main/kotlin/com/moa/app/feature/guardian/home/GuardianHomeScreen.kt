package com.moa.app.feature.guardian.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.product.dialog.MaAlertDialog
import com.moa.app.designsystem.component.product.dialog.MaConfirmDialog
import com.moa.app.designsystem.component.product.topbar.MaHomeTopBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.auth.model.Gender
import com.moa.app.domain.user.model.SeniorProfile
import com.moa.app.feature.guardian.home.component.SeniorProfileGrid
import com.moa.app.feature.guardian.home.model.DialogState
import com.moa.app.feature.guardian.home.model.GuardianHomeUiState
import kotlinx.collections.immutable.persistentListOf

@Composable
fun GuardianHomeScreen(
    viewModel: GuardianHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GuardianHomeContent(
        uiState = uiState,
        onAlertClick = {},
        onSettingClick = viewModel::navigateToSetting,
        onProfileClick = viewModel::navigateToReport,
        toggleDeletable = viewModel::updateDeletable,
        onDeleteClick = viewModel::showDeleteDialog,
        onAddClick = viewModel::navigateToUserConnection
    )

    when (val dialogState = uiState.dialogState) {
        is DialogState.None -> Unit

        is DialogState.DeleteConfirm -> {
            MaAlertDialog(
                title = "${dialogState.profile.name}를 삭제하시겠습니까?",
                content = "정보를 삭제하면 관련된 정보가 모두 사라지며, 이후에는 복구할 수 없습니다.",
                textAlign = TextAlign.Start,
                confirmButtonText = "삭제하기",
                dismissButtonText = "취소",
                onConfirm = viewModel::deleteProfile,
                onDismiss = viewModel::hideDialog,
            )
        }

        is DialogState.DeleteComplete -> {
            MaConfirmDialog(
                title = "삭제가 완료되었습니다.",
                confirmButtonText = "확인",
                onConfirm = viewModel::hideDialog,
                onDialogDismissRequest = viewModel::hideDialog,
            )
        }
    }
}

@Composable
private fun GuardianHomeContent(
    modifier: Modifier = Modifier,
    uiState: GuardianHomeUiState,
    onAlertClick: () -> Unit,
    onSettingClick: () -> Unit,
    onProfileClick: (Long) -> Unit,
    toggleDeletable: () -> Unit,
    onDeleteClick: (Long) -> Unit,
    onAddClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MaHomeTopBar(onSettingClick = onSettingClick) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_bell),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        onClick = onAlertClick,
                        role = Role.Button,
                        interactionSource = null,
                        indication = null
                    )
                    .padding(8.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "안녕하세요, ${uiState.userName}님",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            Text(
                text = "누구의 결과를 볼까요?",
                color = MoaTheme.colors.coolGray40,
                style = MoaTheme.typography.body1Medium,
            )
        }

        SeniorProfileGrid(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 36.dp, bottom = 20.dp),
            profiles = uiState.seniorProfiles,
            deletable = uiState.deletable,
            onProfileClick = onProfileClick,
            onDeleteClick = onDeleteClick,
            onAddClick = onAddClick,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MoaTheme.colors.coolGray90)
                .padding(vertical = 10.dp)
                .clickable(
                    onClick = toggleDeletable,
                    role = Role.Button,
                    interactionSource = null,
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "참여자 관리",
                color = MoaTheme.colors.white,
                style = MoaTheme.typography.body2Semibold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var deletable by remember { mutableStateOf(false) }

    GuardianHomeContent(
        uiState = GuardianHomeUiState.INIT.copy(
            seniorProfiles = persistentListOf(
                SeniorProfile(
                    id = 1,
                    name = "김철수",
                    birthDate = "1990-01-01",
                    gender = Gender.MALE,
                    phoneNumber = "010-1234-5678"
                )
            ),
            deletable = deletable,
        ),
        onAlertClick = {},
        onSettingClick = {},
        onProfileClick = {},
        toggleDeletable = { deletable = !deletable },
        onDeleteClick = {},
        onAddClick = {}
    )
}
