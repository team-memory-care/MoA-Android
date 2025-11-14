package com.moa.app.feature.senior.quiz.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.quiz.category.QuizCategory
import com.moa.app.feature.senior.quiz.component.QuizCategoryCard

@Composable
fun QuizCategoryScreen(
    viewModel: QuizCategoryViewModel = hiltViewModel()
) {
    QuizCategoryScreenContent(
        onQuizCategoryClick = viewModel::onCategoryClicked
    )
}

@Composable
private fun QuizCategoryScreenContent(
    onQuizCategoryClick: (QuizCategory) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "퀴즈 선택",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title2Bold
            )

            Text(
                text = "퀴즈는 MMSE 기반으로 만들어졌어요",
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.body1Regular
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            QuizCategory.entries.forEach { category ->
                QuizCategoryCard(
                    title = category.title,
                    description = category.description,
                    backgroundImage = category.imageRes,
                    backgroundColor = category.backgroundColor,
                    modifier = Modifier.weight(1f),
                    onClick = { onQuizCategoryClick(category) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    QuizCategoryScreenContent(
        onQuizCategoryClick = {}
    )
}
