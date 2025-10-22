package com.moa.app.feature.onboarding.signup.auth

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaButtonDefaults
import com.moa.app.designsystem.component.core.textfield.MaTextField
import com.moa.app.designsystem.component.product.layout.FormField
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun SignUpPhoneAuthScreen() {
    SignUpPhoneAuthScreenContent()
}

@Composable
private fun SignUpPhoneAuthScreenContent() {
    var phoneNumber by remember { mutableStateOf("") }
    var authNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoaTheme.colors.white)
    ) {
        MaTopAppBar(
            title = "회원가입",
            onBackClick = {}
        )

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "안전을 위해 전화번호를\n확인할게요",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormField(
                isError = true,
                errorMessage = "어쩌구"
            ) {
                MaTextField(
                    value = phoneNumber,
                    onValueChange = { newValue -> phoneNumber = newValue },
                    isError = true,
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
                            onClick = {},
                            enabled = true,
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
                isError = true,
                errorMessage = "어쩌구",
                modifier = Modifier.alpha(1f)
            ) {
                Text(
                    text = "인증번호",
                    color = MoaTheme.colors.black,
                    style = MoaTheme.typography.title2Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                MaTextField(
                    value = authNumber,
                    onValueChange = { newValue -> authNumber = newValue },
                    isError = true,
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
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            ) {
                Text(
                    text = "다음",
                    style = MoaTheme.typography.body1Medium,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SignUpPhoneAuthScreenContent()
}
