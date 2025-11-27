package com.moa.app.feature.senior.quiz.persistence

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaQuizButton
import com.moa.app.designsystem.component.core.button.QuizButtonState
import com.moa.app.designsystem.component.product.topbar.MaStepProgressTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.R
import com.moa.app.feature.senior.quiz.component.QuizLoadContent
import com.moa.app.feature.senior.quiz.component.QuizResultDialog
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PersistenceQuizScreen(
    viewModel: PersistenceQuizViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val uiState = uiState) {
        is PersistenceQuizUiState.Loading -> QuizLoadContent()
        is PersistenceQuizUiState.Error -> {}
        is PersistenceQuizUiState.Success -> {
            PersistenceQuizContent(
                onBackClick = {},
                uiState = uiState,
                onOptionSelected = viewModel::selectAnswer,
                onContinueClick = viewModel::checkAnswer,
            )

            QuizResultDialog(dialogState = state.resultDialogState)
        }
    }
}

@Composable
private fun PersistenceQuizContent(
    uiState: PersistenceQuizUiState.Success,
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

        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = uiState.currentQuestionIndex,
            label = "QuizSlideAnimation",
            transitionSpec = {
                (slideIntoContainer(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                ) togetherWith slideOutOfContainer(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                ))
                    .using(SizeTransform(clip = true))
            },
        ) { targetIndex ->
            key(targetIndex) {
                val question = uiState.quizzes[targetIndex]
                val quizText = question.questionContent

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MoaTheme.colors.coolGray98,
                                shape = RoundedCornerShape(32.dp),
                            )
                            .padding(vertical = 24.dp, horizontal = 32.dp),
                    ) {
                        Text(
                            text = quizText,
                            color = MoaTheme.colors.black,
                            style = MoaTheme.typography.title2Semibold,
                        )
                    }

                    Image(
                        painter = painterResource(R.drawable.img_quiz_character_top),
                        contentDescription = null,
                        modifier = Modifier.align(alignment = Alignment.End)
                    )

                    question.answerOptions.forEachIndexed { index, option ->
                        val buttonState = when (uiState.selectedAnswerIndex) {
                            null -> QuizButtonState.DEFAULT
                            index -> QuizButtonState.SELECTED
                            else -> QuizButtonState.UNSELECTED
                        }

                        MaQuizButton(
                            onClick = { onOptionSelected(index) },
                            state = buttonState,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = option,
                                style = MoaTheme.typography.body1Semibold,
                                modifier = Modifier.padding(vertical = 16.dp),
                            )
                        }
                        Spacer(Modifier.height(8.dp))
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
    PersistenceQuizContent(
        uiState = PersistenceQuizUiState.Success(
            quizzes = persistentListOf(
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "오늘은 몇 년도인가요?",
                    questionContent = "",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "오늘은 몇 년도인가요?",
                    questionContent = "",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "오늘은 몇 년도인가요?",
                    questionContent = "",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
                PersistenceQuiz(
                    id = 1,
                    type = QuizCategory.PERSISTENCE,
                    questionFormat = "오늘은 몇 년도인가요?",
                    questionContent = "",
                    answer = "2025년",
                    answerOptions = persistentListOf("2025년", "2022년", "2020년"),
                ),
            )
        ),
        onOptionSelected = {},
        onContinueClick = {},
        onBackClick = {},
    )
}
