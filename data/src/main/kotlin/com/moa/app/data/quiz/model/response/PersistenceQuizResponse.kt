package com.moa.app.data.quiz.model.response

import com.moa.app.domain.quiz.model.PersistenceQuiz
import com.moa.app.domain.quiz.model.QuizCategory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PERSISTENCE")
data class PersistenceQuizResponse(
    override val questionId: Long,
    override val questionFormat: String,
    override val questionContent: String,
    override val answer: String,
    @SerialName("answerOptions") val answerOptions: List<String>,
) : QuizResponse()

fun PersistenceQuizResponse.toDomain(): PersistenceQuiz {
    return PersistenceQuiz(
        id = this.questionId,
        type = QuizCategory.PERSISTENCE,
        questionFormat = this.questionFormat,
        questionContent = this.questionContent,
        answer = this.answer,
        answerOptions = this.answerOptions.toImmutableList(),
    )
}
