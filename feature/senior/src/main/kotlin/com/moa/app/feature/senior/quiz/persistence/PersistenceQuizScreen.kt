package com.moa.app.feature.senior.quiz.persistence

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.product.dialog.MaAlertDialog
import com.moa.app.designsystem.component.product.topbar.MaStepProgressTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.component.QuizLoadContent
import com.moa.app.feature.senior.quiz.component.QuizResultDialog
import com.moa.app.feature.senior.quiz.component.QuizSlideAnimation
import com.moa.app.feature.senior.quiz.component.quizform.PersistenceQuizForm
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PersistenceQuizScreen(
    viewModel: PersistenceQuizViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler(enabled = true, onBack = viewModel::onBackClick)

    if (uiState.isLoading && uiState.quizzes.isEmpty()) {
        QuizLoadContent(QuizCategory.PERSISTENCE)
    } else {
        PersistenceQuizContent(
            uiState = uiState,
            onBackClick = viewModel::onBackClick,
            onOptionSelected = viewModel::selectAnswer,
            onContinueClick = viewModel::checkAnswer,
        )
    }

    if (uiState.showResultDialog) {
        uiState.quizResult?.let { result ->
            QuizResultDialog(isCorrect = result.isCorrect, correctAnswer = result.correctAnswer)
        }
    }

    if (uiState.showExitDialog) {
        MaAlertDialog(
            title = "퀴즈를 그만두시나요?",
            content = "그만두면 지금까지\n푼 퀴즈는 저장되지 않아요.",
            confirmButtonText = "계속 풀기",
            dismissButtonText = "그만두기",
            onConfirm = viewModel::onHideExitDialog,
            onDismiss = viewModel::exitQuiz,
            onDialogDismissRequest = viewModel::onHideExitDialog,
        )
    }
}

@Composable
private fun PersistenceQuizContent(
    uiState: PersistenceQuizUiState,
    onOptionSelected: (Int) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MaStepProgressTopAppBar(
            title = "지남력 퀴즈",
            onBackClick = onBackClick,
            totalSteps = uiState.totalSteps,
            currentStep = uiState.currentStep,
        )

        Spacer(modifier = Modifier.height(28.dp))

        uiState.currentQuiz?.let { targetQuiz ->
            QuizSlideAnimation(
                targetState = targetQuiz,
                modifier = Modifier.weight(1f),
            ) { question ->
                PersistenceQuizForm(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    questionContent = question.questionContent,
                    answerOptions = question.answerOptions,
                    selectedAnswerIndex = uiState.selectedAnswerIndex,
                    onOptionSelected = onOptionSelected,
                )
            }
        }

        MaButton(
            onClick = onContinueClick,
            enabled = uiState.isContinueButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
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
private fun Preview() {
    PersistenceQuizContent(
        uiState = PersistenceQuizUiState.INIT.copy(
            quizzes = persistentListOf(
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "",
                    questionContent = "오늘은 몇 년도인가요?",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "",
                    questionContent = "오늘은 몇 년도인가요?",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "",
                    questionContent = "오늘은 몇 년도인가요?",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "",
                    questionContent = "오늘은 몇 년도인가요?",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
            ),
        ),
        onOptionSelected = {},
        onContinueClick = {},
        onBackClick = {},
    )
}
