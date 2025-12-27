package com.moa.app.feature.report.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.product.chart.ChartLegendItems
import com.moa.app.designsystem.component.product.chart.MaBarChart
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.report.model.WeeklyReportUiModel
import com.moa.app.feature.report.model.WeeklyScoreUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Composable
fun WeeklyReportContent(
    weeklyReport: WeeklyReportUiModel,
    onCategorySelected: (QuizCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ReportSummary(
            summary = weeklyReport.oneLineReview,
            completeRate = weeklyReport.completeRate,
            correctRate = weeklyReport.correctRate,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "전주 대비 주간 추세",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.title2Semibold,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        QuizCategorySelector(
            selectedCategory = weeklyReport.selectedCategory,
            onCategorySelected = onCategorySelected,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(38.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MaBarChart(
                items = weeklyReport.chartData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            ChartLegendItems(currentText = "이번 주간", lastText = "지난 주간")
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "주간 진단",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.title2Semibold,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        ReportDetail(detailContent = weeklyReport.diagnosis)

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "다음주 전략",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.title2Semibold,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        ReportDetail(detailContent = weeklyReport.nextWeekStrategy)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeeklyReportContent() {
    var selectedCategory by remember { mutableStateOf(QuizCategory.ALL) }
    WeeklyReportContent(
        modifier = Modifier.fillMaxSize(),
        onCategorySelected = { selectedCategory = it },
        weeklyReport = WeeklyReportUiModel(
            oneLineReview = "이번 주에는 참여율은 꾸준했지만,\n정답률이 지난주보다 조금 떨어졌습니다.",
            completeRate = 80,
            correctRate = 70,
            selectedCategory = selectedCategory,
            scores = persistentMapOf(
                QuizCategory.ALL to persistentListOf(
                    WeeklyScoreUiModel("월", 10, 30),
                    WeeklyScoreUiModel("화", 10, 60),
                    WeeklyScoreUiModel("수", 50, 10),
                    WeeklyScoreUiModel("목", 10, 90),
                    WeeklyScoreUiModel("금", 10, 10),
                    WeeklyScoreUiModel("토", 100, 10),
                    WeeklyScoreUiModel("일", 10, 17),
                ),
                QuizCategory.PERSISTENCE to persistentListOf(
                    WeeklyScoreUiModel("월", 10, 30),
                    WeeklyScoreUiModel("화", 20, 50),
                    WeeklyScoreUiModel("수", 50, 10),
                    WeeklyScoreUiModel("목", 40, 28),
                    WeeklyScoreUiModel("금", 10, 10),
                    WeeklyScoreUiModel("토", 100, 10),
                    WeeklyScoreUiModel("일", 10, 17),
                ),
                QuizCategory.LINGUISTIC to persistentListOf(
                    WeeklyScoreUiModel("월", 10, 30),
                    WeeklyScoreUiModel("화", 10, 60),
                    WeeklyScoreUiModel("수", 50, 10),
                    WeeklyScoreUiModel("목", 10, 90),
                    WeeklyScoreUiModel("금", 10, 10),
                    WeeklyScoreUiModel("토", 100, 10),
                    WeeklyScoreUiModel("일", 10, 17),
                ),
                QuizCategory.MEMORY to persistentListOf(
                    WeeklyScoreUiModel("월", 10, 30),
                    WeeklyScoreUiModel("화", 10, 60),
                    WeeklyScoreUiModel("수", 50, 10),
                    WeeklyScoreUiModel("목", 10, 90),
                    WeeklyScoreUiModel("금", 10, 10),
                    WeeklyScoreUiModel("토", 100, 10),
                    WeeklyScoreUiModel("일", 10, 17),
                ),
                QuizCategory.ATTENTION to persistentListOf(
                    WeeklyScoreUiModel("월", 10, 30),
                    WeeklyScoreUiModel("화", 10, 60),
                    WeeklyScoreUiModel("수", 50, 10),
                    WeeklyScoreUiModel("목", 10, 90),
                    WeeklyScoreUiModel("금", 10, 10),
                    WeeklyScoreUiModel("토", 100, 10),
                    WeeklyScoreUiModel("일", 10, 17),
                ),
                QuizCategory.SPACETIME to persistentListOf(
                    WeeklyScoreUiModel("월", 10, 30),
                    WeeklyScoreUiModel("화", 10, 60),
                    WeeklyScoreUiModel("수", 50, 10),
                    WeeklyScoreUiModel("목", 10, 90),
                    WeeklyScoreUiModel("금", 10, 10),
                    WeeklyScoreUiModel("토", 100, 10),
                    WeeklyScoreUiModel("일", 10, 17),
                ),
            ),
            diagnosis = "기억력은 안정적으로 유지되었지만, 주의력과 언어 영역에서 점진적인 하락이 관찰되었습니다.\n\n" +
                "특히 3주차에 언어 정답률이 크게 떨어지며, 월간 추세 전반에 영향을 준 것으로 보입니다.\n\n" +
                "응답 속도도 점차 늦어져, 단순 변동이 아닌 장기적인 인지 처리 부담이 누적되는 양상이 확인됩니다.",
            nextWeekStrategy = "평소 생활 리듬을 일정하게 유지하고, 집중력이 높은 오전 시간대에 퀴즈를 응시하도록 해보세요." +
                "또한 언어 자극 활동을 늘리는 것이 필요합니다. 가족과 신문 기사 한 단락을 함께 읽고 이야기하거나," +
                "짧은 글쓰기·단어 맞히기 놀이를 추가해보면 효과적입니다.",
        ),
    )
}
