package com.moa.app.feature.senior.quiz.internal

import android.content.Context
import coil3.ImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import com.moa.app.domain.quiz.model.QuizCategory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizImagePreloader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader,
) {
    suspend fun preload(urls: Collection<String>, category: QuizCategory): Boolean =
        coroutineScope {
            urls.distinct()
                .filter { it.isNotBlank() }
                .map { url -> async { preload(url, category) } }
                .awaitAll()
                .all { it }
        }

    private suspend fun preload(url: String, category: QuizCategory): Boolean {
        val request = ImageRequest.Builder(context)
            .data(url)
            .taggedAsQuiz(category)
            .build()

        return try {
            when (val result = imageLoader.execute(request)) {
                is SuccessResult -> true
                is ErrorResult -> {
                    Timber.w(result.throwable, "Failed to preload quiz image: $url")
                    false
                }
            }
        } catch (t: Throwable) {
            if (t is CancellationException) throw t
            Timber.w(t, "Failed to preload quiz image: $url")
            false
        }
    }
}
