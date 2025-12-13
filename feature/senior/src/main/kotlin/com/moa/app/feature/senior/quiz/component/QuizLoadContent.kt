package com.moa.app.feature.senior.quiz.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.R
import com.moa.app.feature.senior.quiz.category.model.loadBackgroundColor
import com.moa.app.feature.senior.quiz.category.model.loadDescription
import com.moa.app.feature.senior.quiz.category.model.loadReadyColor
import com.moa.app.feature.senior.quiz.category.model.loadTitleColor

@Composable
fun QuizLoadContent(
    category: QuizCategory,
    modifier: Modifier = Modifier,
) {
    Box {
        Image(
            painter = painterResource(com.moa.app.designsystem.R.drawable.img_load_quiz),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(category.loadBackgroundColor)
        )

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.img_quiz_load_character),
                contentDescription = null,
                modifier = Modifier.offset(y = 1.dp),
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(
                        color = MoaTheme.colors.white,
                        shape = RoundedCornerShape(32.dp),
                    )
                    .padding(vertical = 26.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = category.loadDescription,
                    color = category.loadTitleColor,
                    style = MoaTheme.typography.headLine2Bold,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "퀴즈가 곧 시작돼요...",
                    color = category.loadReadyColor,
                    style = MoaTheme.typography.body2Medium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    QuizLoadContent(
        category = QuizCategory.PERSISTENCE,
    )
}
