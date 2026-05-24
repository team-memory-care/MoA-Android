package com.moa.app.feature.senior.quiz.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil3.Extras
import coil3.request.ImageRequest
import com.moa.app.domain.quiz.model.QuizCategory

internal val QuizCategoryKey: Extras.Key<QuizCategory?> = Extras.Key(default = null)

internal fun ImageRequest.Builder.taggedAsQuiz(category: QuizCategory): ImageRequest.Builder =
    apply { extras[QuizCategoryKey] = category }

@Composable
internal fun rememberQuizImageRequest(data: Any?, category: QuizCategory): ImageRequest {
    val context = LocalContext.current
    return remember(data, category) {
        ImageRequest.Builder(context)
            .data(data)
            .taggedAsQuiz(category)
            .build()
    }
}
