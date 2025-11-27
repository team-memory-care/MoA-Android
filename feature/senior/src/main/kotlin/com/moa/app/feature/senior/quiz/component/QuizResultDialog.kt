package com.moa.app.feature.senior.quiz.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.R

@Composable
fun QuizResultDialog(
    isCorrect: Boolean,
    correctAnswer: String,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        QuizResultCard(
            isCorrect = isCorrect,
            correctAnswer = correctAnswer,
            modifier = modifier
        )
    }
}

@Composable
private fun QuizResultCard(
    isCorrect: Boolean,
    correctAnswer: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor: Color = if (isCorrect) MoaTheme.colors.blue100 else MoaTheme.colors.red200
    val imageRes: Int = if (isCorrect) R.drawable.img_quiz_correct else R.drawable.img_quiz_incorrect
    val title: String = if (isCorrect) "정답이에요!" else "오답이에요..."
    val titleColor: Color = if (isCorrect) MoaTheme.colors.blue500 else MoaTheme.colors.red600
    val description: String = if (isCorrect) "좋아요 다음 문제도 풀어봐요" else "정답은 ${correctAnswer}이에요"
    val descriptionColor: Color = if (isCorrect) MoaTheme.colors.blue400 else MoaTheme.colors.red400

    Column(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 40.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
        )

        Text(
            text = title,
            color = titleColor,
            style = MoaTheme.typography.headLine2Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 4.dp)
        )

        Text(
            text = description,
            color = descriptionColor,
            style = MoaTheme.typography.title2Semibold
        )
    }
}

@Preview(name = "정답")
@Composable
private fun PreviewCorrect() {
    QuizResultCard(isCorrect = true, correctAnswer = "")
}

@Preview(name = "오답")
@Composable
private fun PreviewIncorrect() {
    QuizResultCard(isCorrect = false, correctAnswer = "2025년")
}
