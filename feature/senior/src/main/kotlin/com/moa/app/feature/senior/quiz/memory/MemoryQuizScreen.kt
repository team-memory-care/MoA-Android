package com.moa.app.feature.senior.quiz.memory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.product.dialog.MaAlertDialog
import com.moa.app.designsystem.component.product.topbar.MaStepProgressTopAppBar
import com.moa.app.domain.quiz.model.MemoryQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.component.QuizLoadContent
import com.moa.app.feature.senior.quiz.component.QuizResultDialog
import com.moa.app.feature.senior.quiz.component.QuizSlideAnimation
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizPlayContent
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizReadyContent
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizTextModeContent
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizVoiceModeContent
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MemoryQuizScreen(
    viewModel: MemoryQuizViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler(enabled = true, onBack = viewModel::onBackClick)

    if (uiState.isLoading && uiState.quizzes.isEmpty()) {
        QuizLoadContent(QuizCategory.MEMORY)
    } else {
        MemoryQuizContent(
            uiState = uiState,
            onStartQuizClick = viewModel::displayQuizImages,
            onStartSpeakingClick = viewModel::startListening,
            onChangeModeClick = viewModel::switchToTextMode,
            onUnableToSpeakClick = viewModel::displayChangeModeButton,
            onContinueTextClick = viewModel::checkTextAnswer,
            onTextAnswerChange = viewModel::updateTextAnswer,
            onBackClick = viewModel::onBackClick,
            onImagesFinished = viewModel::onImagesFinished,
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
private fun MemoryQuizContent(
    uiState: MemoryQuizUiState,
    modifier: Modifier = Modifier,
    onStartQuizClick: () -> Unit,
    onStartSpeakingClick: () -> Unit,
    onChangeModeClick: () -> Unit,
    onUnableToSpeakClick: () -> Unit,
    onTextAnswerChange: (Int, String) -> Unit,
    onContinueTextClick: () -> Unit,
    onImagesFinished: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        MaStepProgressTopAppBar(
            title = "기억력 퀴즈",
            onBackClick = onBackClick,
            totalSteps = uiState.totalSteps,
            currentStep = uiState.currentStep,
        )

        Spacer(modifier = Modifier.height(24.dp))

        uiState.currentQuiz?.let { targetQuiz ->
            QuizSlideAnimation(
                targetState = targetQuiz,
                modifier = Modifier.weight(1f),
            ) { question ->
                when (uiState.quizState) {
                    MemoryQuizSetState.WAITING_TO_START -> {
                        MemoryQuizReadyContent(onContinueClick = onStartQuizClick)
                    }

                    MemoryQuizSetState.QUESTION_DISPLAY -> {
                        MemoryQuizPlayContent(
                            imageUrls = targetQuiz.imageUrls,
                            onImagesFinished = onImagesFinished
                        )
                    }

                    MemoryQuizSetState.ANSWERING -> {
                        when (uiState.inputMode) {
                            InputMode.VOICE -> {
                                MemoryQuizVoiceModeContent(
                                    isSpeaking = uiState.isSpeaking,
                                    showChangeModeButton = uiState.isChangeModeButtonEnabled,
                                    onStartSpeakingClick = onStartSpeakingClick,
                                    onUnableToSpeakClick = onUnableToSpeakClick,
                                    onChangeModeClick = onChangeModeClick,
                                )
                            }

                            InputMode.TEXT -> {
                                MemoryQuizTextModeContent(
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    onContinueClick = onContinueTextClick,
                                    answers = uiState.userTextAnswers,
                                    onTextAnswerChange = { index, answer ->
                                        onTextAnswerChange(index, answer)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoryQuizContentPreview() {
    MemoryQuizContent(
        uiState = MemoryQuizUiState.INIT.copy(
            quizState = MemoryQuizSetState.WAITING_TO_START,
            quizzes = persistentListOf(
                MemoryQuiz(
                    id = 0,
                    type = QuizCategory.MEMORY,
                    questionFormat = "",
                    questionContent = "",
                    answer = listOf("사과", "당근", "의자"),
                    imageUrls = listOf(
                        "https://moa-bucket-s3.s3.ap-northeast-2.amazonaws.com/8d120aaa-0_memory_2.png",
                        "https://moa-bucket-s3.s3.ap-northeast-2.amazonaws.com/03548c44-e_memory_4.png",
                        "https://moa-bucket-s3.s3.ap-northeast-2.amazonaws.com/37300c63-c_memory_8.png",
                    ),
                ),
            ),
        ),
        onStartQuizClick = {},
        onStartSpeakingClick = {},
        onChangeModeClick = {},
        onUnableToSpeakClick = {},
        onContinueTextClick = {},
        onTextAnswerChange = { _, _ -> },
        onBackClick = {},
        onImagesFinished = {},
    )
}
