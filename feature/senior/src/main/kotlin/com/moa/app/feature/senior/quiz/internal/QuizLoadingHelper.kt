package com.moa.app.feature.senior.quiz.internal

import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.usecase.FetchQuizUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

internal const val MIN_LOADING_DELAY_MS = 2000L

internal suspend fun <T> fetchWithMinDelay(
    minDelayMs: Long = MIN_LOADING_DELAY_MS,
    fetch: suspend () -> Result<T>,
): Result<T> =
    coroutineScope {
        val minDelay = async { delay(minDelayMs) }
        val data = async { fetch() }
        minDelay.await()
        data.await()
    }

internal suspend inline fun <reified Q : Quiz> loadQuizzesWithMinDelay(
    category: QuizCategory,
    fetchQuizUseCase: FetchQuizUseCase,
    minDelayMs: Long = MIN_LOADING_DELAY_MS,
): Result<ImmutableList<Q>> =
    fetchWithMinDelay(minDelayMs) { fetchQuizUseCase(category) }
        .map { all -> all.filterIsInstance<Q>().toImmutableList() }
