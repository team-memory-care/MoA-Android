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
internal const val QUIZ_RESULT_DISPLAY_MS = 2000L

internal suspend fun <T> fetchWithMinDelay(
    minDelayMs: Long = MIN_LOADING_DELAY_MS,
    onResultReady: suspend (T) -> Unit = {},
    fetch: suspend () -> Result<T>,
): Result<T> =
    coroutineScope {
        val minDelay = async { delay(minDelayMs) }
        val data = async {
            fetch().also { it.getOrNull()?.let { value -> onResultReady(value) } }
        }
        minDelay.await()
        data.await()
    }

internal suspend inline fun <reified Q : Quiz> loadQuizzesWithMinDelay(
    category: QuizCategory,
    fetchQuizUseCase: FetchQuizUseCase,
    minDelayMs: Long = MIN_LOADING_DELAY_MS,
): Result<ImmutableList<Q>> =
    loadQuizzesWithMinDelay(
        category = category,
        fetchQuizUseCase = fetchQuizUseCase,
        minDelayMs = minDelayMs,
        onQuizzesReady = {},
    )

internal suspend inline fun <reified Q : Quiz> loadQuizzesWithMinDelay(
    category: QuizCategory,
    fetchQuizUseCase: FetchQuizUseCase,
    minDelayMs: Long = MIN_LOADING_DELAY_MS,
    crossinline onQuizzesReady: suspend (ImmutableList<Q>) -> Unit,
): Result<ImmutableList<Q>> =
    fetchWithMinDelay(
        minDelayMs = minDelayMs,
        onResultReady = { all -> onQuizzesReady(all.filterIsInstance<Q>().toImmutableList()) },
        fetch = { fetchQuizUseCase(category) },
    ).map { all -> all.filterIsInstance<Q>().toImmutableList() }
