package com.moa.app.feature.onboarding.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.textfield.MaOtpTextField
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.onboarding.connection.model.UserConnectionUiState

@Composable
fun UserConnectionScreen(
    viewModel: UserConnectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserConnectionContent(
        uiState = uiState,
        onChangedUserCode = viewModel::updateUserCode,
        onBackClick = viewModel::navigateToBack,
        onNextClick = viewModel::navigateToNext
    )
}

@Composable
fun UserConnectionContent(
    uiState: UserConnectionUiState,
    onChangedUserCode: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaTopAppBar(title = "회원코드 입력", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Text(
                text = uiState.getTitle,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            MaOtpTextField(
                otpText = uiState.userCode,
                onOtpTextChange = { onChangedUserCode(it) },
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .fillMaxWidth(),
                enabled = !uiState.isUserSenior,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        MaButton(
            onClick = onNextClick,
            enabled = uiState.isNextEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
        ) {
            Text(
                text = "다음",
                style = MoaTheme.typography.body1Medium,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    UserConnectionContent(
        uiState = UserConnectionUiState.INIT,
        onChangedUserCode = {},
        onBackClick = {},
        onNextClick = {}
    )
}
