package com.moa.app.feature.senior.quiz.component.quizform

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moa.app.feature.senior.quiz.memory.InputMode
import com.moa.app.feature.senior.quiz.memory.MemoryQuizSetState
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizPlayContent
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizReadyContent
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizTextModeContent
import com.moa.app.feature.senior.quiz.memory.component.MemoryQuizVoiceModeContent

@Composable
fun MemoryQuizForm(
    quizState: MemoryQuizSetState,
    inputMode: InputMode,
    isSpeaking: Boolean,
    isChangeModeButtonEnabled: Boolean,
    imageUrls: List<String>,
    currentImageIndex: Int,
    userTextAnswers: List<String>,
    isTextContinueButtonEnabled: Boolean,
    onStartQuizClick: () -> Unit,
    onImageClick: () -> Unit,
    onStartSpeakingClick: () -> Unit,
    onUnableToSpeakClick: () -> Unit,
    onChangeModeClick: () -> Unit,
    onContinueTextClick: () -> Unit,
    onTextAnswerChange: (Int, String) -> Unit,
) {
    when (quizState) {
        MemoryQuizSetState.WAITING_TO_START -> {
            MemoryQuizReadyContent(onContinueClick = onStartQuizClick)
        }

        MemoryQuizSetState.QUESTION_DISPLAY -> {
            MemoryQuizPlayContent(
                imageUrls = imageUrls,
                currentImageIndex = currentImageIndex,
            )
        }

        MemoryQuizSetState.ANSWERING -> {
            when (inputMode) {
                InputMode.VOICE -> {
                    MemoryQuizVoiceModeContent(
                        isSpeaking = isSpeaking,
                        showChangeModeButton = isChangeModeButtonEnabled,
                        onStartSpeakingClick = onStartSpeakingClick,
                        onUnableToSpeakClick = onUnableToSpeakClick,
                        onImageClick = onImageClick,
                        onChangeModeClick = onChangeModeClick,
                    )
                }

                InputMode.TEXT -> {
                    MemoryQuizTextModeContent(
                        answers = userTextAnswers,
                        isContinueEnabled = isTextContinueButtonEnabled,
                        onTextAnswerChange = onTextAnswerChange,
                        onImageClick = onImageClick,
                        onContinueClick = onContinueTextClick,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMemoryQuizForm() {
    MemoryQuizForm(
        quizState = MemoryQuizSetState.WAITING_TO_START,
        inputMode = InputMode.VOICE,
        isSpeaking = false,
        isChangeModeButtonEnabled = false,
        imageUrls = listOf("", "", ""),
        currentImageIndex = 0,
        userTextAnswers = listOf("", "", ""),
        isTextContinueButtonEnabled = false,
        onStartQuizClick = {},
        onImageClick = {},
        onStartSpeakingClick = {},
        onUnableToSpeakClick = {},
        onChangeModeClick = {},
        onContinueTextClick = {},
        onTextAnswerChange = { _, _ -> },
    )
}
