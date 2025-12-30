package com.moa.app.feature.report.model

import androidx.compose.runtime.Immutable
import com.moa.app.designsystem.component.product.chart.BarChartItem
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.report.model.WeeklyReport
import com.moa.app.domain.report.model.WeeklyScore
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

@Immutable
data class WeeklyReportUiModel(
    val oneLineReview: String,
    val completeRate: Int,
    val correctRate: Int,
    val diagnosis: String,
    val scores: ImmutableMap<QuizCategory, ImmutableList<WeeklyScoreUiModel>>,
    val nextWeekStrategy: String,
    val selectedCategory: QuizCategory = QuizCategory.ALL,
) {
    val currentCategoryScores: ImmutableList<WeeklyScoreUiModel>
        get() = scores[selectedCategory] ?: persistentListOf()

    val chartData: ImmutableList<BarChartItem>
        get() = currentCategoryScores.map { scoreModel ->
            BarChartItem(
                label = scoreModel.dayOfWeek,
                primaryValue = scoreModel.score,
                secondaryValue = scoreModel.lastWeekScore,
            )
        }.toImmutableList()
}

@Immutable
data class WeeklyScoreUiModel(
    val dayOfWeek: String,
    val score: Long,
    val lastWeekScore: Long,
)

fun WeeklyReport.toUiModel(): WeeklyReportUiModel {
    return WeeklyReportUiModel(
        oneLineReview = oneLineReview,
        completeRate = completeRate,
        correctRate = correctRate,
        diagnosis = diagnosis,
        scores = scores.mapValues { (_, weeklyScores) ->
            weeklyScores.map { it.toUiModel() }.toImmutableList()
        }.toImmutableMap(),
        nextWeekStrategy = nextWeekStrategy,
    )
}

private fun WeeklyScore.toUiModel(): WeeklyScoreUiModel {
    return WeeklyScoreUiModel(
        dayOfWeek = dayOfWeek,
        score = score,
        lastWeekScore = lastWeekScore,
    )
}
