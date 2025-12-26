package com.moa.app.feature.report.model

import androidx.compose.runtime.Immutable
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.report.model.DailyQuizScore
import com.moa.app.domain.report.model.DailyReport
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class DailyReportUiModel(
    val date: String,
    val dailyQuizScore: ImmutableList<DailyQuizScoreUiModel>,
    val advices: ImmutableList<String>,
)

@Immutable
data class DailyQuizScoreUiModel(
    val type: QuizCategory,
    val correctNumber: Int,
    val totalNumber: Int,
)

fun DailyReport.toUiModel(): DailyReportUiModel {
    return DailyReportUiModel(
        date = date,
        dailyQuizScore = dailyQuizScore.map { it.toUiModel() }.toImmutableList(),
        advices = advices,
    )
}

private fun DailyQuizScore.toUiModel(): DailyQuizScoreUiModel {
    return DailyQuizScoreUiModel(
        type = type,
        correctNumber = correctNumber,
        totalNumber = totalNumber,
    )
}
