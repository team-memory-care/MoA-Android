package com.moa.app.data.quiz.model.request

import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.quiz.model.QuizScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizScoreRequest(
    @SerialName("totalNumber") val totalNumber: Int,
    @SerialName("correctNumber") val correctNumber: Int,
    @SerialName("type") val type: QuizCategory
)

fun QuizScore.toDto(): QuizScoreRequest {
    return QuizScoreRequest(
        totalNumber = totalNumber,
        correctNumber = correctNumber,
        type = type,
    )
}
