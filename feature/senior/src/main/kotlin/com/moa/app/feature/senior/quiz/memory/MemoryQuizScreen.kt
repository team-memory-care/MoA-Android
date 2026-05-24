package com.moa.app.feature.senior.quiz.memory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
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
import com.moa.app.ui.extension.quizMaxWidth
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun MemoryQuizScreen(
    viewModel: MemoryQuizViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler(enabled = true, onBack = viewModel::onBackClick)

    val currentQuizId = uiState.currentQuiz?.id
    LaunchedEffect(currentQuizId) {
        if (currentQuizId == null) return@LaunchedEffect
        snapshotFlow {
            Triple(uiState.quizState, uiState.inputMode, uiState.isLoading)
        }
            .filter { (quizState, _, isLoading) ->
                quizState == MemoryQuizSetState.ANSWERING && !isLoading
            }
            .distinctUntilChanged()
            .collect { viewModel.speakCurrentQuestion() }
    }

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
            onImageClick = viewModel::speakCurrentQuestion,
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
private fun MemoryQuizContent(
    uiState: MemoryQuizUiState,
    modifier: Modifier = Modifier,
    onStartQuizClick: () -> Unit,
    onStartSpeakingClick: () -> Unit,
    onChangeModeClick: () -> Unit,
    onUnableToSpeakClick: () -> Unit,
    onTextAnswerChange: (Int, String) -> Unit,
    onContinueTextClick: () -> Unit,
    onImageClick: () -> Unit,
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

        Column(
            modifier = Modifier
                .quizMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) {
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
                                currentImageIndex = uiState.displayImageIndex,
                            )
                        }

                        MemoryQuizSetState.ANSWERING -> {
                            when (uiState.inputMode) {
                                InputMode.VOICE -> {
                                    MemoryQuizVoiceModeContent(
                                        isSpeaking = uiState.isSpeaking,
                                        showChangeModeButton = uiState.isChangeModeButtonEnabled,
                                        onImageClick = onImageClick,
                                        onStartSpeakingClick = onStartSpeakingClick,
                                        onUnableToSpeakClick = onUnableToSpeakClick,
                                        onChangeModeClick = onChangeModeClick,
                                    )
                                }

                                InputMode.TEXT -> {
                                    MemoryQuizTextModeContent(
                                        onImageClick = onImageClick,
                                        answers = uiState.userTextAnswers,
                                        isContinueEnabled = uiState.isTextContinueButtonEnabled,
                                        onTextAnswerChange = onTextAnswerChange,
                                        onContinueClick = onContinueTextClick,
                                    )
                                }
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
                    questionFormat = "",
                    questionContent = "",
                    answer = listOf("사과", "당근", "의자"),
                    imageUrls = listOf("", "", ""),
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
        onImageClick = {},
    )
}
