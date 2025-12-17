package com.moa.app.data.quiz.service

import com.moa.app.data.quiz.model.request.QuizScoreRequest
import com.moa.app.data.quiz.model.response.QuizResponse
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.network.model.NetworkResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QuizService {

    @GET("/api/v1/quiz/set")
    suspend fun fetchQuizzes(@Query("type") type: QuizCategory): NetworkResult<List<QuizResponse>>

    @POST("/api/v1/quiz/result")
    suspend fun uploadQuizScore(@Body request: QuizScoreRequest): NetworkResult<Unit>
}
