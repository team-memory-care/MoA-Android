package com.moa.app.domain.quiz.usecase

import com.moa.app.domain.quiz.model.QuizResult
import com.moa.app.domain.quiz.repository.QuizRepository
import javax.inject.Inject

class SubmitQuizResultUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(quizResult: QuizResult): Result<Unit> {
        return quizRepository.submitQuizResult(quizResult)
    }
}
