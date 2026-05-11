package com.moa.app.data.quiz.model.response

import com.moa.app.domain.quiz.model.SpaceTimeQuiz
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("SPACETIME")
data class SpaceTimeQuizResponse(
    override val questionId: Long,
    override val questionFormat: String,
    override val questionContent: String,
    @SerialName("answer") val answer: String,
    @SerialName("questionImageUrl") val questionImageUrl: String,
    @SerialName("imageOptionsUrl") val imageOptionsUrl: List<String>
) : QuizResponse()

fun SpaceTimeQuizResponse.toDomain(): SpaceTimeQuiz {
    return SpaceTimeQuiz(
        id = this.questionId,
        questionFormat = this.questionFormat,
        questionContent = this.questionContent,
        answerIndex = this.answer.toInt() - 1,
        questionImageUrl = this.questionImageUrl,
        imageOptionsUrl = this.imageOptionsUrl.toPersistentList()
    )
}
