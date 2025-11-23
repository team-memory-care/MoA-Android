package com.moa.app.feature.onboarding.role

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaSelectButton
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.auth.model.UserRole
import com.moa.app.feature.onboarding.role.model.SelectUserSideEffect
import com.moa.app.feature.onboarding.role.model.SelectUserUiState

@Composable
fun SelectUserRoleScreen(
    viewModel: SelectUserRoleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SelectUserSideEffect.ShowToast -> {
                        Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    SelectUserRoleScreenContent(
        uiState = uiState,
        onSeniorRoleClick = viewModel::updateUserRole,
        onGuardianRoleClick = viewModel::updateUserRole,
        onBackClick = viewModel::navigateToBack,
        onNextClick = viewModel::navigateToUserConnection
    )
}

@Composable
private fun SelectUserRoleScreenContent(
    uiState: SelectUserUiState,
    onSeniorRoleClick: (UserRole) -> Unit,
    onGuardianRoleClick: (UserRole) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        MaTopAppBar(title = "", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = "모아 사용 목적을\n선택해주세요",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            Spacer(Modifier.height(28.dp))

            MaSelectButton(
                onClick = { onSeniorRoleClick(UserRole.PARENT) },
                selected = uiState.isUserRoleSenior,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.img_role_senior),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(126.dp, 112.dp)
                    )

                    Spacer(Modifier.height(18.dp))

                    Text(
                        text = "저는 본인이에요",
                        style = MoaTheme.typography.title2Semibold,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    Text(
                        text = "인지 능력을 키우고 싶어요",
                        style = MoaTheme.typography.body1Medium,
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            MaSelectButton(
                onClick = { onGuardianRoleClick(UserRole.CHILD) },
                selected = uiState.isUserRoleGuardian,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.img_role_guardian),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(126.dp, 112.dp)
                    )

                    Spacer(Modifier.height(18.dp))

                    Text(
                        text = "저는 가족이나 보호자에요",
                        style = MoaTheme.typography.title2Semibold,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    Text(
                        text = "상대방의 리포트를 확인하고 싶어요",
                        style = MoaTheme.typography.body1Medium,
                    )
                }
            }
        }

        MaButton(
            onClick = onNextClick,
            enabled = uiState.isNextEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
        ) {
            Text(
                text = "다음",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SelectUserRoleScreenContent(
        uiState = SelectUserUiState.INIT,
        onSeniorRoleClick = {},
        onGuardianRoleClick = {},
        onBackClick = {},
        onNextClick = {}
    )
}
