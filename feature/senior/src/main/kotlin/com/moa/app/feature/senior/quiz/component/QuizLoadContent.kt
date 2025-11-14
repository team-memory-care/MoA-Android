package com.moa.app.feature.senior.quiz.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.R

@Composable
fun QuizLoadContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MoaTheme.colors.blue100),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_quiz_load_character),
            contentDescription = null,
            modifier = Modifier.offset(y = 1.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(
                    color = MoaTheme.colors.white,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "지남력은\n시간과 장소를 확인해요",
                color = MoaTheme.colors.blue700,
                style = MoaTheme.typography.headLine2Bold
            )

            Text(
                text = "퀴즈가 곧 시작돼요...",
                color = MoaTheme.colors.blue200,
                style = MoaTheme.typography.body2Medium
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    QuizLoadContent()
}
