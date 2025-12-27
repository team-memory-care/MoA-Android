package com.moa.app.data.report.model.response

import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.report.model.MonthReportScore
import com.moa.app.domain.report.model.MonthlyReport
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyReportResponse(
    @SerialName("oneLineReview") val oneLineReview: String,
    @SerialName("completeRate") val completeRate: Int,
    @SerialName("correctRate") val correctRate: Int,
    @SerialName("diagnosis") val diagnosis: String,
    @SerialName("score") val score: Map<QuizCategory, MonthReportScoreDto>,
    @SerialName("longTermStrategy") val longTermStrategy: String
)

@Serializable
data class MonthReportScoreDto(
    @SerialName("weekIndex") val weekIndex: Int,
    @SerialName("score") val score: Long,
    @SerialName("lastMonthScore") val lastMonthScore: Long,
)

fun MonthlyReportResponse.toDomain(): MonthlyReport {
    return MonthlyReport(
        oneLineReview = this.oneLineReview,
        completeRate = this.completeRate,
        correctRate = this.correctRate,
        diagnosis = this.diagnosis,
        score = this.score.mapValues { it.value.toDomain() },
        longTermStrategy = this.longTermStrategy
    )
}

fun MonthReportScoreDto.toDomain(): MonthReportScore {
    return MonthReportScore(
        weekIndex = this.weekIndex,
        score = this.score,
        lastMonthScore = this.lastMonthScore
    )
}
