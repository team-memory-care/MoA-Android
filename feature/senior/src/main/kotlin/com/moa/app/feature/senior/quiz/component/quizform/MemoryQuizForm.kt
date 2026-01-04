package com.moa.app.feature.senior.quiz.component.quizform

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    userTextAnswers: List<String>,
    onStartQuizClick: () -> Unit,
    onImagesFinished: () -> Unit,
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
                onImagesFinished = onImagesFinished
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
                        onChangeModeClick = onChangeModeClick,
                    )
                }

                InputMode.TEXT -> {
                    MemoryQuizTextModeContent(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        onContinueClick = onContinueTextClick,
                        answers = userTextAnswers,
                        onTextAnswerChange = onTextAnswerChange
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
        userTextAnswers = listOf("", "", ""),
        onStartQuizClick = {},
        onImagesFinished = {},
        onStartSpeakingClick = {},
        onUnableToSpeakClick = {},
        onChangeModeClick = {},
        onContinueTextClick = {},
        onTextAnswerChange = { _, _ -> },
    )
}
