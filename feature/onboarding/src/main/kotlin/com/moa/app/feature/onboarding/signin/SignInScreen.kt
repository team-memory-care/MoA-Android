package com.moa.app.feature.onboarding.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaButtonColors
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    SignInScreenContent(
        navigateToSignUp = viewModel::navigateToSignUp
    )
}

@Composable
private fun SignInScreenContent(
    navigateToSignUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.img_moa_logo),
            contentDescription = null,
            modifier = Modifier.weight(1f),
        )

        MaButton(
            onClick = navigateToSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
        ) {
            Text(
                text = "처음이에요",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            )
        }

        MaButton(
            onClick = { },
            colors = MaButtonColors(
                defaultBackground = Color.Transparent,
                pressedBackground = Color.Transparent,
                disabledBackground = Color.Transparent,
                defaultContentColor = MoaTheme.colors.coolGray60,
                pressedContentColor = MoaTheme.colors.coolGray60,
                disabledContentColor = MoaTheme.colors.coolGray60,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
        ) {
            Text(
                text = "이미 계정이 있어요",
                style = MoaTheme.typography.body2Medium,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SignInScreenContent(
        navigateToSignUp = {}
    )
}
