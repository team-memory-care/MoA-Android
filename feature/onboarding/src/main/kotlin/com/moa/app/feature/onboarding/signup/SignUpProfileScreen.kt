package com.moa.app.feature.onboarding.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaSelectButton
import com.moa.app.designsystem.component.core.textfield.MaTextField
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.onboarding.signup.state.SignUpProfileUiState

@Composable
fun SignUpProfileScreen(
    viewModel: SignUpSharedViewModel,
) {
    val uiState by viewModel.signUpUserProfileUiState.collectAsStateWithLifecycle()

    SignUpProfileScreenContent(
        uiState = uiState,
        onChangeName = viewModel::updateName,
        onChangeYear = viewModel::updateYear,
        onChangeMonth = viewModel::updateMonth,
        onChangeDay = viewModel::updateDay,
        onSelectedMale = viewModel::selectMaleGender,
        onSelectedFemale = viewModel::selectFemaleGender,
        onBackClick = viewModel::navigateToBack,
        onNextClick = viewModel::navigateToNext,
    )
}

@Composable
private fun SignUpProfileScreenContent(
    uiState: SignUpProfileUiState,
    onChangeName: (String) -> Unit,
    onChangeYear: (String) -> Unit,
    onChangeMonth: (String) -> Unit,
    onChangeDay: (String) -> Unit,
    onSelectedMale: () -> Unit,
    onSelectedFemale: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val nameFocusRequester = remember { FocusRequester() }
    val yearFocusRequester = remember { FocusRequester() }
    val monthFocusRequester = remember { FocusRequester() }
    val dayFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        nameFocusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaTopAppBar(
            title = "회원가입",
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
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
                modifier = Modifier.padding(bottom = 12.dp),
            )

            MaTextField(
                value = uiState.name,
                onValueChange = onChangeName,
                modifier = Modifier.focusRequester(nameFocusRequester),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
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
                modifier = Modifier.padding(bottom = 12.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MaTextField(
                    value = uiState.year,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }.take(4)
                        onChangeYear(filtered)

                        if (filtered.length == 4) { monthFocusRequester.requestFocus() }
                    },
                    modifier = Modifier
                        .weight(1.2f)
                        .focusRequester(yearFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
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
                    value = uiState.month,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }.take(2)
                        onChangeMonth(filtered)

                        if (filtered.length == 2) { dayFocusRequester.requestFocus() }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(monthFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
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
                    value = uiState.day,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }.take(2)
                        onChangeDay(filtered)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(dayFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
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
                modifier = Modifier.padding(bottom = 12.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MaSelectButton(
                    onClick = onSelectedFemale,
                    selected = uiState.isGenderFemale,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "여자",
                        style = MoaTheme.typography.body1Medium,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                    )
                }

                MaSelectButton(
                    onClick = onSelectedMale,
                    selected = uiState.isGenderMale,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "남자",
                        style = MoaTheme.typography.body1Medium,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            MaButton(
                onClick = onNextClick,
                enabled = uiState.isNextEnabled,
                modifier = Modifier
                    .fillMaxWidth()
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
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SignUpProfileScreenContent(
        uiState = SignUpProfileUiState.init,
        onChangeName = {},
        onChangeYear = {},
        onChangeMonth = {},
        onChangeDay = {},
        onSelectedMale = {},
        onSelectedFemale = {},
        onBackClick = {},
        onNextClick = {},
    )
}
