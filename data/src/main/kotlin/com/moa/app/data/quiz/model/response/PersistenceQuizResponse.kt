package com.moa.app.data.quiz.model.response

import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersistenceQuizResponse(
    @SerialName("questionId") val questionId: Long,
    @SerialName("quizType") val quizType: String,
    @SerialName("questionFormat") val questionFormat: String,
    @SerialName("questionContent") val questionContent: String,
    @SerialName("answer") val answer: String,
    @SerialName("answerOptions") val answerOptions: List<String>
)

fun PersistenceQuizResponse.toDomain(): PersistenceQuiz {
    return PersistenceQuiz(
        id = this.questionId,
        type = QuizCategory.fromString(this.quizType),
        questionFormat = this.questionFormat,
        questionContent = this.questionContent,
        answer = this.answer,
        answerOptions = this.answerOptions.toImmutableList()
    )
}
