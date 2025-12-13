package com.moa.app.data.quiz.repositoryImpl

import com.moa.app.data.quiz.datasource.QuizDataSource
import com.moa.app.data.quiz.model.response.toDomain
import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizResult
import com.moa.app.domain.quiz.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDataSource: QuizDataSource
) : QuizRepository {

    override suspend fun fetchQuizzes(category: QuizCategory): Result<List<Quiz>> {
        return quizDataSource.fetchQuizzes(category)
            .mapCatching { responses ->
                responses.map { it.toDomain() }
            }
    }

    override suspend fun submitQuizResult(quizResult: QuizResult): Result<Unit> {
        return quizDataSource.submitQuizResult(quizResult)
    }
}
