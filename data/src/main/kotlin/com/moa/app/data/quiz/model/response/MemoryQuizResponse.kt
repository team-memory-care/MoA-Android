package com.moa.app.data.quiz.model.response

import com.moa.app.domain.quiz.model.MemoryQuiz
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MEMORY")
data class MemoryQuizResponse(
    override val questionId: Long,
    override val questionFormat: String,
    override val questionContent: String,
    @SerialName("answer") val answer: List<String>,
    @SerialName("imageUrls") val imageUrls: List<String>,
    @SerialName("inputMethod") val inputMethod: String,
    @SerialName("requiredSequenceType") val requiredSequenceType: String,
) : QuizResponse()

fun MemoryQuizResponse.toDomain(): MemoryQuiz {
    return MemoryQuiz(
        id = this.questionId,
        questionFormat = this.questionFormat,
        questionContent = this.questionContent,
        answer = this.answer,
        imageUrls = this.imageUrls,
    )
}
