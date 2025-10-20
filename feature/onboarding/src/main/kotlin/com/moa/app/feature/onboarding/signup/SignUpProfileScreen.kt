package com.moa.app.feature.onboarding.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaSelectButton
import com.moa.app.designsystem.component.core.textfield.MaTextField
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun SignUpProfileScreen() {
    SignUpProfileScreenContent()
}

@Composable
private fun SignUpProfileScreenContent() {
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

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
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "가입을 위해\n기본 정보를 확인할게요",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "이름",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            MaTextField(
                value = name,
                onValueChange = { newValue -> name = newValue },
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "이름을 입력해주세요.",
                        color = MoaTheme.colors.coolGray60,
                        style = MoaTheme.typography.body1Medium,
                    )
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "생년월일",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MaTextField(
                    value = year,
                    onValueChange = { newValue -> year = newValue },
                    modifier = Modifier.weight(2f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    placeholder = {
                        Text(
                            text = "YYYY",
                            color = MoaTheme.colors.coolGray60,
                            style = MoaTheme.typography.body1Medium,
                        )
                    },
                )

                MaTextField(
                    value = month,
                    onValueChange = { newValue -> month = newValue },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    placeholder = {
                        Text(
                            text = "MM",
                            color = MoaTheme.colors.coolGray60,
                            style = MoaTheme.typography.body1Medium,
                        )
                    },
                )

                MaTextField(
                    value = day,
                    onValueChange = { newValue -> day = newValue },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    placeholder = {
                        Text(
                            text = "DD",
                            color = MoaTheme.colors.coolGray60,
                            style = MoaTheme.typography.body1Medium,
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "성별",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MaSelectButton(
                    onClick = { selectedGender = "female" },
                    selected = selectedGender == "female",
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "여자",
                        style = MoaTheme.typography.body1Medium,
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 20.dp)
                    )
                }

                MaSelectButton(
                    onClick = { selectedGender = "male" },
                    selected = selectedGender == "male",
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "남자",
                        style = MoaTheme.typography.body1Medium,
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 20.dp)
                    )
                }
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
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SignUpProfileScreenContent()
}
