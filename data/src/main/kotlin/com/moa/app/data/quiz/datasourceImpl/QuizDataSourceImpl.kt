package com.moa.app.data.quiz.datasourceImpl

import com.moa.app.data.quiz.datasource.QuizDataSource
import com.moa.app.data.quiz.model.request.toDto
import com.moa.app.data.quiz.model.response.QuizResponse
import com.moa.app.data.quiz.service.QuizService
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizResult
import com.moa.app.network.extension.toResult
import javax.inject.Inject

class QuizDataSourceImpl @Inject constructor(
    private val quizService: QuizService,
) : QuizDataSource {

    override suspend fun fetchQuizzes(type: QuizCategory): Result<List<QuizResponse>> {
        return quizService.fetchQuizzes((type)).toResult { it }
    }

    override suspend fun submitQuizResult(quizResult: QuizResult): Result<Unit> {
        return quizService.submitQuizResult(quizResult.toDto()).toResult()
    }

}
