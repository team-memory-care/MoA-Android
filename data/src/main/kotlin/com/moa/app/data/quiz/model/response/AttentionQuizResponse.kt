package com.moa.app.data.quiz.model.response

import com.moa.app.domain.quiz.model.AttentionQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ATTENTION")
data class AttentionQuizResponse(
    override val questionId: Long,
    override val questionFormat: String,
    override val questionContent: String,
    override val answer: String,
    @SerialName("expression") val expression: String,
    @SerialName("inputType") val inputType: String,
) : QuizResponse()

fun AttentionQuizResponse.toDomain(): AttentionQuiz {
    return AttentionQuiz(
        id = this.questionId,
        type = QuizCategory.ATTENTION,
        questionFormat = this.questionFormat,
        questionContent = this.questionContent,
        answer = this.answer,
        expression = this.expression,
        inputType = this.inputType,
    )
}
