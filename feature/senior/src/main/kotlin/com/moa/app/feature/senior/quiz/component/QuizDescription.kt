package com.moa.app.feature.senior.quiz.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.R

@Composable
fun CenterQuizDescription(
    quizDescription: String,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.img_quiz_character_center),
            contentDescription = null,
            modifier = Modifier.clickable(
                onClick = onImageClick,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        )

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp)
                .background(
                    color = MoaTheme.colors.coolGray98,
                    shape = RoundedCornerShape(32.dp),
                )
                .padding(vertical = 16.dp, horizontal = 24.dp),
        ) {
            Text(
                text = quizDescription,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Semibold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CenterQuizDescription(
        quizDescription = "아래의 그림은\n무엇일까요?",
        onImageClick = {},
        modifier = Modifier.height(120.dp),
    )
}
