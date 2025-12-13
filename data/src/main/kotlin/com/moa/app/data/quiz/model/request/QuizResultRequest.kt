package com.moa.app.data.quiz.model.request

import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizResultRequest(
    @SerialName("totalNumber") val totalNumber: Int,
    @SerialName("correctNumber") val correctNumber: Int,
    @SerialName("type") val type: QuizCategory
)

fun QuizResult.toDto(): QuizResultRequest {
    return QuizResultRequest(
        totalNumber = totalNumber,
        correctNumber = correctNumber,
        type = type,
    )
}
