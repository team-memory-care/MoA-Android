package com.moa.app.feature.senior.quiz.daily

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.moa.app.domain.quiz.model.AttentionQuiz
import com.moa.app.domain.quiz.model.LinguisticQuiz
import com.moa.app.domain.quiz.model.MemoryQuiz
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.SpaceTimeQuiz
import com.moa.app.feature.senior.quiz.component.quizform.AttentionQuizForm
import com.moa.app.feature.senior.quiz.component.quizform.LinguisticQuizForm
import com.moa.app.feature.senior.quiz.component.QuizLoadContent
import com.moa.app.feature.senior.quiz.component.QuizResultDialog
import com.moa.app.feature.senior.quiz.component.QuizSlideAnimation
import com.moa.app.feature.senior.quiz.component.quizform.MemoryQuizForm
import com.moa.app.feature.senior.quiz.component.quizform.PersistenceQuizForm
import com.moa.app.feature.senior.quiz.component.quizform.SpaceTimeQuizForm
import com.moa.app.feature.senior.quiz.memory.InputMode
import com.moa.app.feature.senior.quiz.memory.MemoryQuizSetState

@Composable
fun DailyQuizScreen(
    viewModel: DailyQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler(enabled = true, onBack = viewModel::onBackClick)

    if (uiState.isLoading) {
        QuizLoadContent(QuizCategory.ALL)
    } else {
        DailyQuizContent(
            uiState = uiState,
            onOptionSelected = viewModel::selectAnswer,
            onInputChanged = viewModel::updateAnswer,
            onDeleteClick = viewModel::deleteAnswer,
            onStartQuizClick = viewModel::displayQuizImages,
            onImagesFinished = viewModel::onImagesFinished,
            onStartSpeakingClick = viewModel::startListening,
            onChangeModeClick = viewModel::switchToTextMode,
            onUnableToSpeakClick = viewModel::displayChangeModeButton,
            onTextAnswerChange = viewModel::updateTextAnswer,
            onContinueClick = viewModel::checkAnswer,
            onBackClick = viewModel::exitQuiz,
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
private fun DailyQuizContent(
    modifier: Modifier = Modifier,
    uiState: DailyQuizUiState,
    onOptionSelected: (Int) -> Unit, // 지남력, 언어능력, 시공간 정답 입력
    onInputChanged: (String) -> Unit, // 집중력 퀴즈 정답 입력
    onDeleteClick: () -> Unit, // 집중력 입력 값 초기화
    onStartQuizClick: () -> Unit,
    onImagesFinished: () -> Unit,
    onStartSpeakingClick: () -> Unit,
    onChangeModeClick: () -> Unit,
    onUnableToSpeakClick: () -> Unit,
    onTextAnswerChange: (Int, String) -> Unit,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MaStepProgressTopAppBar(
            title = "오늘의 퀴즈",
            onBackClick = onBackClick,
            totalSteps = uiState.totalSteps,
            currentStep = uiState.currentStep,
        )

        uiState.currentQuiz?.let { targetQuiz ->
            QuizSlideAnimation(
                targetState = targetQuiz,
                modifier = Modifier.weight(1f),
            ) { quiz ->
                when (quiz) {
                    is PersistenceQuiz -> {
                        PersistenceQuizForm(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            questionContent = quiz.questionContent,
                            answerOptions = quiz.answerOptions,
                            selectedAnswerIndex = uiState.selectedAnswerIndex,
                            onOptionSelected = onOptionSelected
                        )
                    }

                    is LinguisticQuiz -> {
                        LinguisticQuizForm(
                            questionImage = quiz.questionImage,
                            answerOptions = quiz.answerOptions,
                            selectedAnswerIndex = uiState.selectedAnswerIndex,
                            onOptionClick = onOptionSelected,
                        )
                    }

                    is MemoryQuiz -> {
                        MemoryQuizForm(
                            quizState = uiState.memoryQuizState,
                            inputMode = uiState.memoryQuizInputMode,
                            isSpeaking = uiState.isSpeaking,
                            isChangeModeButtonEnabled = uiState.isChangeModeButtonEnabled,
                            imageUrls = quiz.imageUrls,
                            userTextAnswers = uiState.memoryQuizTextAnswers,
                            onStartQuizClick = onStartQuizClick,
                            onImagesFinished = onImagesFinished,
                            onStartSpeakingClick = onStartSpeakingClick,
                            onUnableToSpeakClick = onUnableToSpeakClick,
                            onChangeModeClick = onChangeModeClick,
                            onContinueTextClick = onContinueClick,
                            onTextAnswerChange = onTextAnswerChange,
                        )
                    }

                    is AttentionQuiz -> {
                        AttentionQuizForm(
                            question = quiz.expression,
                            input = uiState.attentionQuizAnswer,
                            maxInputLength = quiz.answer.length,
                            onInputChanged = onInputChanged,
                            onDeleteClick = onDeleteClick,
                            onImageClick = {}
                        )
                    }

                    is SpaceTimeQuiz -> {
                        SpaceTimeQuizForm(
                            questionImageUrl = quiz.questionImageUrl,
                            imageOptionsUrl = quiz.imageOptionsUrl,
                            selectedAnswerIndex = uiState.selectedAnswerIndex,
                            onOptionSelected = onOptionSelected
                        )
                    }
                }
            }
        }

        if (uiState.currentQuiz !is MemoryQuiz) {
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewDailyQuizContent() {
    DailyQuizContent(
        uiState = DailyQuizUiState.INIT,
        onOptionSelected = {},
        onInputChanged = {},
        onDeleteClick = {},
        onStartQuizClick = {},
        onImagesFinished = {},
        onStartSpeakingClick = {},
        onChangeModeClick = {},
        onUnableToSpeakClick = {},
        onTextAnswerChange = { _, _ -> },
        onBackClick = {},
        onContinueClick = {},
    )
}
