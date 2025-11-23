package com.moa.app.feature.onboarding.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaButtonDefaults
import com.moa.app.designsystem.component.core.textfield.MaTextField
import com.moa.app.designsystem.component.product.layout.FormField
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.onboarding.signup.model.SignUpPhoneAuthUiState

@Composable
fun SignUpPhoneAuthScreen(
    viewModel: SignUpSharedViewModel,
) {
    val uiState by viewModel.signUpPhoneAuthUiState.collectAsStateWithLifecycle()

    SignUpPhoneAuthScreenContent(
        uiState = uiState,
        onChangePhoneNumber = viewModel::updatePhoneNumber,
        onAuthCodeRequestClick = viewModel::requestAuthCode,
        onChangeAuthNumber = viewModel::updateAuthCode,
        onAuthConfirmClick = viewModel::verifyAuthCode,
        onBackClick = viewModel::navigateToBack,
    )
}

@Composable
private fun SignUpPhoneAuthScreenContent(
    uiState: SignUpPhoneAuthUiState,
    onChangePhoneNumber: (String) -> Unit,
    onAuthCodeRequestClick: () -> Unit,
    onChangeAuthNumber: (String) -> Unit,
    onAuthConfirmClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val phoneNumberFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        phoneNumberFocusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaTopAppBar(
            title = "회원가입",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = "안전을 위해 전화번호를\n확인할게요",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormField(
                isError = uiState.isPhoneNumberError,
                errorMessage = uiState.phoneNumberErrorMessage,
            ) {
                MaTextField(
                    value = uiState.phoneNumber,
                    onValueChange = onChangePhoneNumber,
                    modifier = Modifier.focusRequester(phoneNumberFocusRequester),
                    isError = uiState.isPhoneNumberError,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    placeholder = {
                        Text(
                            text = "예) 010-1234-5678",
                            color = MoaTheme.colors.coolGray60,
                            style = MoaTheme.typography.body1Medium,
                        )
                    },
                    trailingContent = {
                        MaButton(
                            onClick = onAuthCodeRequestClick,
                            enabled = !uiState.isAuthCodeRequested,
                            colors = MaButtonDefaults.maBlackButtonColors(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "인증",
                                style = MoaTheme.typography.body2Bold,
                                color = MoaTheme.colors.white,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            FormField(
                isError = uiState.isAuthCodeError,
                errorMessage = uiState.authCodeErrorMessage,
                modifier = Modifier.alpha(if (uiState.isAuthCodeRequested) 1f else 0f)
            ) {
                Text(
                    text = "인증번호",
                    color = MoaTheme.colors.black,
                    style = MoaTheme.typography.title2Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                MaTextField(
                    value = uiState.authCode,
                    onValueChange = onChangeAuthNumber,
                    isError = uiState.isAuthCodeError,
                    maxLines = 1,
                    placeholder = {
                        Text(
                            text = "4자리 인증번호를 입력해주세요.",
                            color = MoaTheme.colors.coolGray60,
                            style = MoaTheme.typography.body1Medium,
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            MaButton(
                onClick = onAuthConfirmClick,
                enabled = uiState.authCode.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            ) {
                Text(
                    text = "인증 확인",
                    style = MoaTheme.typography.body1Bold,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SignUpPhoneAuthScreenContent(
        uiState = SignUpPhoneAuthUiState.init,
        onChangePhoneNumber = {},
        onAuthCodeRequestClick = {},
        onChangeAuthNumber = {},
        onAuthConfirmClick = {},
        onBackClick = {},
    )
}
