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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.textfield.MaOtpTextField
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun UserConnectionScreen(
    viewModel: UserConnectionViewModel = hiltViewModel()
) {
    UserConnectionContent(
        onNextClick = viewModel::navigateToSeniorHome
    )
}

@Composable
fun UserConnectionContent(
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaTopAppBar(
            title = "회원코드 입력",
            onBackClick = {},
        )

        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Text(
                text = "상대방의 회원코드를\n입력해 연결해주세요",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            MaOtpTextField(
                otpText = "",
                onOtpTextChange = {},
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        MaButton(
            onClick = onNextClick,
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
        onNextClick = {}
    )
}
