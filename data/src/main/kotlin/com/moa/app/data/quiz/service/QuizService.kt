package com.moa.app.data.quiz.service

import com.moa.app.data.quiz.model.response.PersistenceQuizResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {

    @GET("/api/v1/quiz/set")
    suspend fun fetchPersistenceQuizzes(
        @Query("type") type: String
    ): NetworkResult<List<PersistenceQuizResponse>>

}
