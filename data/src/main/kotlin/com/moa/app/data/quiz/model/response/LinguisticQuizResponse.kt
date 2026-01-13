package com.moa.app.data.quiz.model.response

import com.moa.app.domain.quiz.model.LinguisticQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("LINGUISTIC")
data class LinguisticQuizResponse(
    override val questionId: Long,
    override val questionFormat: String,
    override val questionContent: String,
    @SerialName("answer") val answer: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("answerOptions") val answerOptions: List<String>,
) : QuizResponse()

fun LinguisticQuizResponse.toDomain(): LinguisticQuiz {
    return LinguisticQuiz(
        id = this.questionId,
        type = QuizCategory.LINGUISTIC,
        questionFormat = this.questionFormat,
        questionContent = this.questionContent,
        questionImage = this.imageUrl,
        answer = this.answer,
        answerOptions = this.answerOptions.toImmutableList(),
    )
}
