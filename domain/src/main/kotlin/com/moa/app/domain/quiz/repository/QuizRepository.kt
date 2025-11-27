package com.moa.app.domain.quiz.repository

import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory

interface QuizRepository {
    suspend fun fetchPersistenceQuizzes(category: QuizCategory): Result<List<PersistenceQuiz>>
}
