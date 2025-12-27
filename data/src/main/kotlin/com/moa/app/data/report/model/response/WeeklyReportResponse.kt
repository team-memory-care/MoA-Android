package com.moa.app.data.report.model.response

import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.report.model.WeeklyReport
import com.moa.app.domain.report.model.WeeklyScore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyReportResponse(
    @SerialName("oneLineReview") val oneLineReview: String,
    @SerialName("completeRate") val completeRate: Int,
    @SerialName("correctRate") val correctRate: Int,
    @SerialName("diagnosis") val diagnosis: String,
    @SerialName("scores") val scores: Map<QuizCategory, List<WeeklyScoreDto>>,
    @SerialName("nextWeekStrategy") val nextWeekStrategy: String
)

@Serializable
data class WeeklyScoreDto(
    @SerialName("dayOfWeek") val dayOfWeek: String,
    @SerialName("score") val score: Long,
    @SerialName("lastWeekScore") val lastWeekScore: Long,
)

fun WeeklyReportResponse.toDomain(): WeeklyReport {
    return WeeklyReport(
        oneLineReview = this.oneLineReview,
        completeRate = this.completeRate,
        correctRate = this.correctRate,
        diagnosis = this.diagnosis,
        scores = this.scores.mapValues { (_, weeklyScores) ->
            weeklyScores.map { it.toDomain() }
        },
        nextWeekStrategy = this.nextWeekStrategy
    )
}

fun WeeklyScoreDto.toDomain(): WeeklyScore {
    return WeeklyScore(
        dayOfWeek = this.dayOfWeek,
        score = this.score,
        lastWeekScore = this.lastWeekScore,
    )
}
