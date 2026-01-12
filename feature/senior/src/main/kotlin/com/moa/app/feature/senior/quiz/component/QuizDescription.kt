package com.moa.app.feature.senior.quiz.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.R
import com.moa.app.ui.extension.clickableWithoutRipple

@Composable
fun TopQuizDescription(
    quizDescription: String,
    modifier: Modifier = Modifier,
    alignImageEnd: Boolean = false,
) {
    val alignment = if (alignImageEnd) Alignment.End else Alignment.Start
    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 104.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(MoaTheme.colors.coolGray98)
                .padding(vertical = 24.dp, horizontal = 32.dp),
        ) {
            Text(
                text = quizDescription,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Semibold
            )
        }

        Image(
            painter = painterResource(R.drawable.img_quiz_character_top),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 44.dp)
                .align(alignment = alignment)
        )
    }
}

@Composable
fun CenterQuizDescription(
    quizDescription: String,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.img_quiz_character_center),
            contentDescription = null,
            modifier = Modifier
                .clickableWithoutRipple(onClick = onImageClick, role = Role.Button),
        )

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 120.dp)
                .padding(vertical = 12.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(color = MoaTheme.colors.coolGray98)
                .padding(vertical = 16.dp, horizontal = 24.dp),
        ) {
            Text(
                text = quizDescription,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Semibold
            )
        }
    }
}

@Composable
fun BottomQuizDescription(
    quizDescription: String,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.img_quiz_character_bottom),
            contentDescription = null,
            modifier = Modifier
                .clickableWithoutRipple(onClick = onImageClick, role = Role.Button),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(color = MoaTheme.colors.coolGray98)
                .padding(vertical = 38.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = quizDescription,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.display2Bold,
            )
        }
    }
}

@Preview(showBackground = true)
@PreviewFontScale
@Composable
private fun PreviewTopQuizDescription() {
    TopQuizDescription(
        quizDescription = "방금 나온 단어를\n순서대로 말씀해주세요!"
    )
}

@Preview(showBackground = true)
@PreviewFontScale
@Composable
private fun Preview() {
    CenterQuizDescription(
        quizDescription = "아래의 그림은\n무엇일까요?",
        onImageClick = {},
    )
}

@Preview(showBackground = true)
@PreviewFontScale
@Composable
private fun PreviewBottomQuizDescription() {
    BottomQuizDescription(
        quizDescription = "100-7은?",
        onImageClick = {},
    )
}
