package com.moa.app.data.quiz.datasource

import com.moa.app.data.quiz.model.response.QuizResponse
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizResult

interface QuizDataSource {
    suspend fun fetchQuizzes(type: QuizCategory): Result<List<QuizResponse>>
    suspend fun submitQuizResult(quizResult: QuizResult): Result<Unit>
}
