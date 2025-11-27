package com.moa.app.data.quiz.repositoryImpl

import com.moa.app.data.quiz.datasource.QuizDataSource
import com.moa.app.data.quiz.model.response.toDomain
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDataSource: QuizDataSource
) : QuizRepository {
    override suspend fun fetchPersistenceQuizzes(category: QuizCategory): Result<List<PersistenceQuiz>> {
        return quizDataSource.fetchPersistenceQuizzes(category.toString())
            .mapCatching { response ->
                response.map { it.toDomain() }
            }
    }
}
