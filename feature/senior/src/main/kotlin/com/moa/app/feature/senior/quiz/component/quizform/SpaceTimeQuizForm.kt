package com.moa.app.feature.senior.quiz.component.quizform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaQuizButton
import com.moa.app.designsystem.component.core.button.QuizButtonState
import com.moa.app.feature.senior.quiz.component.CenterQuizDescription
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SpaceTimeQuizForm(
    modifier: Modifier = Modifier,
    questionImageUrl: String,
    imageOptionsUrl: ImmutableList<String>,
    selectedAnswerIndex: Int?,
    onOptionSelected: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        CenterQuizDescription(
            quizDescription = "겹치는 모양을\n찾아주세요!",
            onImageClick = {},
            modifier = Modifier.height(120.dp),
        )

        AsyncImage(
            model = questionImageUrl,
            placeholder = painterResource(R.drawable.img_default_card_2),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            imageOptionsUrl.forEachIndexed { index, option ->
                val buttonState = when (selectedAnswerIndex) {
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

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSpaceTimeQuizForm() {
    val previewHandler = AsyncImagePreviewHandler { request ->
        val drawable = ContextCompat.getDrawable(request.context, R.drawable.img_default_card)!!
        drawable.asImage()
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        SpaceTimeQuizForm(
            questionImageUrl = "",
            imageOptionsUrl = persistentListOf("", ""),
            selectedAnswerIndex = null,
            onOptionSelected = {},
        )
    }
}
