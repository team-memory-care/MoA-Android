package com.moa.app.feature.senior.quiz.linguistic

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.product.dialog.MaAlertDialog
import com.moa.app.designsystem.component.product.topbar.MaStepProgressTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.LinguisticQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.component.QuizLoadContent
import com.moa.app.feature.senior.quiz.component.QuizResultDialog
import com.moa.app.feature.senior.quiz.component.QuizSlideAnimation
import com.moa.app.feature.senior.quiz.component.quizform.LinguisticQuizForm
import com.moa.app.feature.senior.quiz.linguistic.model.LinguisticQuizUiState
import com.moa.app.ui.extension.quizMaxWidth
import com.moa.app.ui.preview.FoldablePreviews
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Composable
fun LinguisticQuizScreen(
    viewModel: LinguisticQuizViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BackHandler(enabled = true, onBack = viewModel::onBackClick)

    val currentQuizId = uiState.currentQuiz?.id
    LaunchedEffect(currentQuizId) {
        if (currentQuizId == null) return@LaunchedEffect

        snapshotFlow { uiState.isLoading }
            .filter { isLoading -> !isLoading }
            .first()

        viewModel.speakCurrentQuestion()
    }

    if (uiState.isLoading && uiState.quizzes.isEmpty()) {
        QuizLoadContent(QuizCategory.LINGUISTIC)
    } else {
        LinguisticQuizContent(
            uiState = uiState,
            onOptionClick = viewModel::selectAnswer,
            onImageClick = viewModel::speakCurrentQuestion,
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
    onImageClick: () -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MaStepProgressTopAppBar(
            title = "언어능력 퀴즈",
            onBackClick = onBackClick,
            totalSteps = uiState.totalSteps,
            currentStep = uiState.currentStep,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .quizMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            uiState.currentQuiz?.let { targetQuiz ->
                QuizSlideAnimation(
                    targetState = targetQuiz,
                    modifier = Modifier.weight(1f),
                ) { question ->
                    LinguisticQuizForm(
                        questionImage = question.questionImage,
                        answerOptions = question.answerOptions,
                        selectedAnswerIndex = uiState.selectedAnswerIndex,
                        onImageClick = onImageClick,
                        onOptionClick = onOptionClick,
                    )
                }
            }

            MaButton(
                onClick = onContinueClick,
                enabled = uiState.isContinueButtonEnabled,
                modifier = Modifier.fillMaxWidth()
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

@OptIn(ExperimentalCoilApi::class)
@FoldablePreviews
@Preview(showBackground = true)
@Composable
private fun Preview() {

    val previewHandler = AsyncImagePreviewHandler { request ->
        val drawable = ContextCompat.getDrawable(request.context, R.drawable.img_default_card)!!
        drawable.asImage()
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        LinguisticQuizContent(
            uiState = LinguisticQuizUiState.INIT.copy(
                quizzes = persistentListOf(
                    LinguisticQuiz(
                        questionImage = "",
                        answerOptions = persistentListOf("A", "B", "C", "D"),
                        id = 1,
                        type = QuizCategory.LINGUISTIC,
                        questionFormat = "",
                        questionContent = "",
                        answer = "",
                    ),
                ),
            ),
            onImageClick = {},
            onOptionClick = {},
            onContinueClick = {},
            onBackClick = {},
        )
    }
}
