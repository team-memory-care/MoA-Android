package com.moa.app.feature.senior.quiz.component.quizform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.feature.senior.quiz.attention.component.KeyPadContent
import com.moa.app.feature.senior.quiz.component.BottomQuizDescription

@Composable
fun AttentionQuizForm(
    modifier: Modifier = Modifier,
    question: String,
    input: String,
    maxInputLength: Int,
    onInputChanged: (String) -> Unit,
    onImageClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(modifier = modifier) {
        BottomQuizDescription(
            quizDescription = "${question}은?",
            onImageClick = onImageClick,
            modifier = Modifier.padding(horizontal = 60.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        KeyPadContent(
            input = input,
            onInputChanged = onInputChanged,
            onDeleteClick = onDeleteClick,
            maxInputLength = maxInputLength,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAttentionQuizForm() {
    AttentionQuizForm(
        question = "2 + 2",
        input = "4",
        maxInputLength = 2,
        onInputChanged = {},
        onDeleteClick = {},
        onImageClick = {}
    )
}
