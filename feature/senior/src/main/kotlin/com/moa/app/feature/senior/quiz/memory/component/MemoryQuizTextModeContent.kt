package com.moa.app.feature.senior.quiz.memory.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.textfield.MaOutLineTextField
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.quiz.component.CenterQuizDescription

@Composable
fun MemoryQuizTextModeContent(
    modifier: Modifier = Modifier,
    answers: List<String>,
    onTextAnswerChange: (Int, String) -> Unit,
    onContinueClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequesters = remember { List(answers.size) { FocusRequester() } }
    val labels = listOf("첫번째", "두번째", "세번째")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterQuizDescription(
            modifier = Modifier.height(120.dp),
            quizDescription = "들었던 단어를\n밑에 써주세요!",
            onImageClick = {},
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            answers.forEachIndexed { index, answer ->
                MaOutLineTextField(
                    value = answer,
                    onValueChange = { newValue -> onTextAnswerChange(index, newValue) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequesters[index]),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = if (index < answers.size - 1) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters.getOrNull(index + 1)?.requestFocus() },
                        onDone = { focusManager.clearFocus() }
                    ),
                    placeholder = {
                        Text(
                            text = "${labels[index]} 단어를 작성해주세요",
                            color = MoaTheme.colors.coolGray80,
                            style = MoaTheme.typography.body1Medium
                        )
                    },
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        MaButton(
            onClick = onContinueClick,
            enabled = true,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "계속",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMemoryQuizTextModeContent() {
    MemoryQuizTextModeContent(
        answers = listOf("", "", ""),
        onTextAnswerChange = { _, _ -> },
        onContinueClick = {},
    )
}
