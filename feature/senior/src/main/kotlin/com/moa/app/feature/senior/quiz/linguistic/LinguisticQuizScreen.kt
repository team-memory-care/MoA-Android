package com.moa.app.feature.senior.quiz.linguistic

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
import com.moa.app.feature.senior.quiz.linguistic.model.LinguisticQuizUiState

@Composable
fun LinguisticQuizScreen(
    viewModel: LinguisticQuizViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler(enabled = true, onBack = viewModel::onBackClick)

    if (uiState.isLoading && uiState.quizzes.isEmpty()) {
        QuizLoadContent(QuizCategory.LINGUISTIC)
    } else {
        LinguisticQuizContent(
            uiState = uiState,
            onOptionClick = viewModel::selectAnswer,
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
private fun LinguisticQuizContent(
    uiState: LinguisticQuizUiState,
    onOptionClick: (Int) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MaStepProgressTopAppBar(
            title = "언어능력 퀴즈",
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
                        quizDescription = "아래의 그림은\n무엇일까요?",
                        onImageClick = {},
                        modifier = Modifier.height(120.dp),
                    )

                    AsyncImage(
                        model = question.questionImage,
                        placeholder = painterResource(R.drawable.img_default_card),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        question.answerOptions.indices.step(2).forEach { rowStartIndex ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                repeat(2) { colIndex ->
                                    val optionIndex = rowStartIndex + colIndex

                                    if (optionIndex < question.answerOptions.size) {
                                        val buttonState = when (uiState.selectedAnswerIndex) {
                                            null -> QuizButtonState.DEFAULT
                                            optionIndex -> QuizButtonState.SELECTED
                                            else -> QuizButtonState.UNSELECTED
                                        }

                                        MaQuizButton(
                                            onClick = { onOptionClick(optionIndex) },
                                            state = buttonState,
                                            modifier = Modifier.weight(1f),
                                        ) {
                                            Text(
                                                text = question.answerOptions[optionIndex],
                                                color = MoaTheme.colors.black,
                                                style = MoaTheme.typography.title2Semibold,
                                                modifier = Modifier.padding(vertical = 16.dp),
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }



        Spacer(modifier = Modifier.height(20.dp))

        MaButton(
            onClick = onContinueClick,
            enabled = true,
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
    LinguisticQuizContent(
        uiState = LinguisticQuizUiState.INIT,
        onOptionClick = {},
        onContinueClick = {},
        onBackClick = {},
    )
}
