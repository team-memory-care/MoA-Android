package com.moa.app.feature.senior.quiz.spacetime

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaQuizButton
import com.moa.app.designsystem.component.core.button.QuizButtonState
import com.moa.app.designsystem.component.product.dialog.MaAlertDialog
import com.moa.app.designsystem.component.product.topbar.MaStepProgressTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.component.CenterQuizDescription
import com.moa.app.feature.senior.quiz.component.QuizLoadContent
import com.moa.app.feature.senior.quiz.component.QuizResultDialog
import com.moa.app.feature.senior.quiz.component.QuizSlideAnimation
import com.moa.app.feature.senior.quiz.spacetime.model.SpaceTimeQuizUiState

@Composable
fun SpaceTimeQuizScreen(
    viewModel: SpaceTimeQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler(enabled = true, onBack = viewModel::onBackClick)

    if (uiState.isLoading && uiState.quizzes.isEmpty()) {
        QuizLoadContent(QuizCategory.SPACETIME)
    } else {
        SpaceTimeQuizContent(
            uiState = uiState,
            onOptionSelected = viewModel::selectAnswer,
            onContinueClick = viewModel::checkAnswer,
            onBackClick = viewModel::onBackClick,
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
private fun SpaceTimeQuizContent(
    uiState: SpaceTimeQuizUiState,
    onOptionSelected: (Int) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaStepProgressTopAppBar(
            title = "시공간 구조 퀴즈",
            onBackClick = onBackClick,
            totalSteps = uiState.totalSteps,
            currentStep = uiState.currentStep,
        )

        Spacer(modifier = Modifier.height(8.dp))

        uiState.currentQuiz?.let { targetQuiz ->
            QuizSlideAnimation(
                targetState = targetQuiz,
                modifier = Modifier.weight(1f),
            ) { question ->
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                ) {
                    CenterQuizDescription(
                        quizDescription = "겹치는 모양을\n찾아주세요!",
                        onImageClick = {},
                        modifier = Modifier.height(120.dp),
                    )

                    AsyncImage(
                        model = question.questionImageUrl,
                        placeholder = painterResource(R.drawable.img_default_card_2),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        question.imageOptionsUrl.forEachIndexed { index, option ->
                            val buttonState = when (uiState.selectedAnswerIndex) {
                                null -> QuizButtonState.DEFAULT
                                index -> QuizButtonState.SELECTED
                                else -> QuizButtonState.UNSELECTED
                            }

                            MaQuizButton(
                                onClick = { onOptionSelected(index) },
                                state = buttonState,
                                modifier = Modifier.weight(1f),
                            ) {
                                AsyncImage(
                                    model = option,
                                    contentDescription = null,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                            }
                        }
                    }
                }
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
    SpaceTimeQuizContent(
        uiState = SpaceTimeQuizUiState.INIT,
        onOptionSelected = {},
        onContinueClick = {},
        onBackClick = {}
    )
}
