package com.moa.app.domain.quiz.usecase

import com.moa.app.domain.quiz.model.QuizScore
import com.moa.app.domain.quiz.repository.QuizRepository
import javax.inject.Inject

class UploadQuizScoreUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(quizScore: QuizScore): Result<Unit> {
        return quizRepository.uploadQuizScore(quizScore)
    }
}
