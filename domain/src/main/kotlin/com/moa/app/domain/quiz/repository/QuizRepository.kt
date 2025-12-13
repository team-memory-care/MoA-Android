package com.moa.app.domain.quiz.repository

import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizResult

interface QuizRepository {
    suspend fun fetchQuizzes(category: QuizCategory): Result<List<Quiz>>
    suspend fun submitQuizResult(quizResult: QuizResult): Result<Unit>
}
