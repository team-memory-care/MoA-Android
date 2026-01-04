package com.moa.app.feature.senior.quiz.daily

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.component.QuizLoadContent

@Composable
fun DailyQuizScreen(
    viewModel: DailyQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        QuizLoadContent(QuizCategory.ALL)
    } else {
        DailyQuizContent()
    }
}

@Composable
private fun DailyQuizContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

    }
}

@Preview
@Composable
private fun PreviewDailyQuizContent() {
    DailyQuizContent()
}
