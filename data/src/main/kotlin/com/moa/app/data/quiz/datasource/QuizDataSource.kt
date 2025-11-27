package com.moa.app.data.quiz.datasource

import com.moa.app.data.quiz.model.response.PersistenceQuizResponse

interface QuizDataSource {
    suspend fun fetchPersistenceQuizzes(type: String): Result<List<PersistenceQuizResponse>>
}
