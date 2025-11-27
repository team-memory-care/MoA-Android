package com.moa.app.data.quiz.datasourceImpl

import com.moa.app.data.quiz.datasource.QuizDataSource
import com.moa.app.data.quiz.model.response.PersistenceQuizResponse
import com.moa.app.data.quiz.model.response.toDomain
import com.moa.app.data.quiz.service.QuizService
import com.moa.app.network.extension.toResult
import com.moa.app.network.model.NetworkResult
import javax.inject.Inject

class QuizDataSourceImpl @Inject constructor(
    private val quizService: QuizService
) : QuizDataSource {
    override suspend fun fetchPersistenceQuizzes(type: String): Result<List<PersistenceQuizResponse>> {
        return quizService.fetchPersistenceQuizzes(type).toResult { it }
    }
}
