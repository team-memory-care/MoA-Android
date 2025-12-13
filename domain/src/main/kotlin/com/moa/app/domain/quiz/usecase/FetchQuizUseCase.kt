package com.moa.app.domain.quiz.usecase

import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.repository.QuizRepository
import javax.inject.Inject

class FetchQuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository,
) {
    suspend operator fun invoke(category: QuizCategory): Result<List<Quiz>> {
        return quizRepository.fetchQuizzes(category)
    }
}
