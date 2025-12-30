package com.moa.app.data.report.model.response

import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.report.model.DailyQuizScore
import com.moa.app.domain.report.model.DailyReport
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyReportResponse(
    @SerialName("date") val date: String,
    @SerialName("scores") val dailyQuizScore: List<DailyQuizScoreDto>,
    @SerialName("adviceList") val advices: DailyAdvicesDto
)

@Serializable
data class DailyQuizScoreDto(
    @SerialName("type") val type: QuizCategory,
    @SerialName("correctNumber") val correctNumber: Int,
    @SerialName("totalNumber") val totalNumber: Int,
)

@Serializable
data class DailyAdvicesDto(
    @SerialName("advices") val advices: List<String>
)

fun DailyReportResponse.toDomain(): DailyReport {
    return DailyReport(
        date = date,
        dailyQuizScore = dailyQuizScore.map { it.toDomain() }.toImmutableList(),
        advices = advices.advices.toImmutableList()
    )
}

private fun DailyQuizScoreDto.toDomain(): DailyQuizScore {
    return DailyQuizScore(
        type = type,
        correctNumber = correctNumber,
        totalNumber = totalNumber
    )
}
