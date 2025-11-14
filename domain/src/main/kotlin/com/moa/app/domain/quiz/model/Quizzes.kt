package com.moa.app.domain.quiz.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class Quizzes(
    private val items: ImmutableList<Quiz>
) {
    val size: Int
        get() = items.size

    fun getQuizAt(index: Int): Quiz? = items.getOrNull(index)

    fun <R> map(transform: (Quiz) -> R): ImmutableList<R> = items.map(transform).toImmutableList()

    companion object {
        private val EMPTY_QUIZZES = Quizzes(persistentListOf())

        fun from(items: ImmutableList<Quiz>): Quizzes {
            if (items.isEmpty()) return EMPTY_QUIZZES
            return Quizzes(items)
        }
    }
}
