package com.moa.app.feature.senior.quiz.component.quizform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaQuizButton
import com.moa.app.designsystem.component.core.button.QuizButtonState
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.quiz.component.CenterQuizDescription
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun LinguisticQuizForm(
    modifier: Modifier = Modifier,
    questionImage: String,
    answerOptions: ImmutableList<String>,
    selectedAnswerIndex: Int?,
    onOptionClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        CenterQuizDescription(
            quizDescription = "아래의 그림은\n무엇일까요?",
            onImageClick = {},
            modifier = Modifier.height(120.dp),
        )

        AsyncImage(
            model = questionImage,
            placeholder = painterResource(R.drawable.img_default_card),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            answerOptions.indices.step(2).forEach { rowStartIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    repeat(2) { colIndex ->
                        val optionIndex = rowStartIndex + colIndex

                        if (optionIndex < answerOptions.size) {
                            val buttonState = when (selectedAnswerIndex) {
                                null -> QuizButtonState.DEFAULT
                                optionIndex -> QuizButtonState.SELECTED
                                else -> QuizButtonState.UNSELECTED
                            }

                            MaQuizButton(
                                onClick = { onOptionClick(optionIndex) },
                                state = buttonState,
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = answerOptions[optionIndex],
                                    color = MoaTheme.colors.black,
                                    style = MoaTheme.typography.title2Semibold,
                                    modifier = Modifier.padding(vertical = 16.dp),
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLinguisticQuizForm() {
    LinguisticQuizForm(
        questionImage = "",
        answerOptions = persistentListOf("사과", "바나나", "딸기", "책"),
        selectedAnswerIndex = null,
        onOptionClick = {},
    )
}
