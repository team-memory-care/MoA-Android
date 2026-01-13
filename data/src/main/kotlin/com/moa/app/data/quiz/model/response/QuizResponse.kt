package com.moa.app.data.quiz.model.response

import com.moa.app.domain.quiz.model.Quiz
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("quizType")
sealed class QuizResponse {
    @SerialName("questionId") abstract val questionId: Long
    @SerialName("questionFormat") abstract val questionFormat: String
    @SerialName("questionContent") abstract val questionContent: String
}

fun QuizResponse.toDomain(): Quiz {
    return when (this) {
        is PersistenceQuizResponse -> this.toDomain()
        is LinguisticQuizResponse -> this.toDomain()
        is AttentionQuizResponse -> this.toDomain()
        is SpaceTimeQuizResponse -> this.toDomain()
        is MemoryQuizResponse -> this.toDomain()
    }
}
