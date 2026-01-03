package com.moa.app.feature.onboarding.connection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.onboarding.connection.model.ConnectionCheckUiState

@Composable
fun ConnectionCheckScreen(
    viewModel: ConnectionCheckViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConnectionCheckContent(
        uiState = uiState,
        onNextClick = viewModel::connectToSenior,
        onBackClick = viewModel::navigateToBack
    )
}

@Composable
private fun ConnectionCheckContent(
    modifier: Modifier = Modifier,
    uiState: ConnectionCheckUiState,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MaTopAppBar(title = "", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 20.dp),
        ) {
            Text(
                text = "상대방의 정보를\n확인해 주세요.",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
                modifier = Modifier.padding(bottom = 28.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MoaTheme.colors.green50)
                    .border(
                        width = 1.dp,
                        color = MoaTheme.colors.green500,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = uiState.userInfo,
                    color = MoaTheme.colors.green700,
                    style = MoaTheme.typography.title1Bold,
                )

                Text(
                    text = "생년월일 ${uiState.birthDate}",
                    color = MoaTheme.colors.coolGray50,
                    style = MoaTheme.typography.body1Medium,
                )
            }
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
private fun PreviewConnectionCheckContent() {
    ConnectionCheckContent(
        uiState = ConnectionCheckUiState.INIT,
        onNextClick = {},
        onBackClick = {}
    )
}
