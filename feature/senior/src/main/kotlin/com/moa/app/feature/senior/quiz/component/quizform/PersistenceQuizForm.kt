package com.moa.app.feature.senior.quiz.component.quizform

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.button.MaQuizButton
import com.moa.app.designsystem.component.core.button.QuizButtonState
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PersistenceQuizForm(
    modifier: Modifier = Modifier,
    questionContent: String,
    answerOptions: ImmutableList<String>,
    selectedAnswerIndex: Int?,
    onOptionSelected: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(MoaTheme.colors.coolGray98)
                .padding(vertical = 24.dp, horizontal = 32.dp),
        ) {
            Text(
                text = questionContent,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Semibold,
            )
        }

        Image(
            painter = painterResource(R.drawable.img_quiz_character_top),
            contentDescription = null,
            modifier = Modifier.align(alignment = Alignment.End),
        )

        answerOptions.forEachIndexed { index, option ->
            val buttonState = when (selectedAnswerIndex) {
                null -> QuizButtonState.DEFAULT
                index -> QuizButtonState.SELECTED
                else -> QuizButtonState.UNSELECTED
            }

            MaQuizButton(
                onClick = { onOptionSelected(index) },
                state = buttonState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            ) {
                Text(
                    text = option,
                    style = MoaTheme.typography.body1Semibold,
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPersistenceQuizForm() {
    PersistenceQuizForm(
        questionContent = "오늘은 몇 년도인가요?",
        answerOptions = persistentListOf("2025년", "2022년", "2020년"),
        selectedAnswerIndex = null,
        onOptionSelected = {},
    )
}
