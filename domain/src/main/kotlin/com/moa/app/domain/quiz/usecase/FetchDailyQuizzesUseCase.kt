package com.moa.app.domain.quiz.usecase

import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.repository.QuizRepository
import javax.inject.Inject

class FetchDailyQuizzesUseCase @Inject constructor(
    private val quizRepository: QuizRepository,
) {
    suspend operator fun invoke(): Result<List<Quiz>> {
        return quizRepository.fetchDailyQuizzes()
    }
}
