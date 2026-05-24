package com.moa.app.feature.senior.quiz.internal

import android.os.SystemClock
import coil3.EventListener
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import com.moa.app.domain.quiz.model.QuizCategory
import timber.log.Timber

class ImageLoadingLatencyEventListener private constructor(
    private val category: QuizCategory,
) : EventListener() {

    private var startTimeMs: Long = 0L

    override fun onStart(request: ImageRequest) {
        startTimeMs = SystemClock.elapsedRealtime()
    }

    override fun onSuccess(request: ImageRequest, result: SuccessResult) {
        log(request, "SUCCESS", result.dataSource.name)
    }

    override fun onError(request: ImageRequest, result: ErrorResult) {
        val cause = result.throwable::class.simpleName ?: "Unknown"
        log(request, "ERROR($cause)")
    }

    override fun onCancel(request: ImageRequest) {
        log(request, "CANCEL")
    }

    private fun log(request: ImageRequest, outcome: String, source: String = "-") {
        val elapsed = SystemClock.elapsedRealtime() - startTimeMs
        Timber.tag(tagFor(category))
            .i("url=${request.data} result=$outcome source=$source elapsed=${elapsed}ms")
    }

    object Factory : EventListener.Factory {
        override fun create(request: ImageRequest): EventListener {
            val category = request.extras[QuizCategoryKey] ?: return NONE
            return ImageLoadingLatencyEventListener(category)
        }
    }

    private companion object {
        fun tagFor(category: QuizCategory): String = when (category) {
            QuizCategory.MEMORY -> "ImgLatency-Memory"
            QuizCategory.LINGUISTIC -> "ImgLatency-Linguistic"
            QuizCategory.SPACETIME -> "ImgLatency-SpaceTime"
            else -> "ImgLatency-${category.name}"
        }
    }
}
