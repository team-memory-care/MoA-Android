package com.moa.app.domain.quiz.repository

import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.QuizCategory

interface QuizRepository {
    suspend fun fetchQuizzes(category: QuizCategory): Result<List<Quiz>>
}
