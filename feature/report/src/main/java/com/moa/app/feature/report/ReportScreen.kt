package com.moa.app.feature.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.chip.MaBasicChip
import com.moa.app.designsystem.component.core.chip.MaBasicChipDefaults
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.report.component.DailyReportContent
import com.moa.app.feature.report.component.EmptyReportContent
import com.moa.app.feature.report.component.WeeklyReportContent

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportContent(
        uiState = uiState,
        onReportCycleChange = viewModel::setReportCycle,
        onPrevClick = viewModel::onPrevClick,
        onNextClick = viewModel::onNextClick,
        onWeeklyReportCategorySelected = viewModel::weeklyReportQuizCategorySelected,
        onBackClick = viewModel::navigateToBack,
    )
}

@Composable
private fun ReportContent(
    uiState: ReportUiState,
    modifier: Modifier = Modifier,
    onReportCycleChange: (ReportCycle) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onWeeklyReportCategorySelected: (QuizCategory) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MaTopAppBar(title = "인지능력 보고서", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ReportCycle.entries.forEach {
                MaBasicChip(
                    onClick = { onReportCycleChange(it) },
                    selected = uiState.reportCycle == it,
                    colors = MaBasicChipDefaults.maBasicChipColors(
                        defaultBackground = Color.Transparent,
                        selectedBackgroundColor = MoaTheme.colors.coolGray99,
                        selectedContentColor = MoaTheme.colors.black,
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = it.title,
                        style = MoaTheme.typography.body2Bold,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp),
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_circle_arrow_left),
                contentDescription = null,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onPrevClick,
                    role = Role.Button,
                ),
            )

            Text(
                text = uiState.formattedCurrentDate,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.headLine2Bold,
            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_circle_arrow_right),
                contentDescription = null,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onNextClick,
                    role = Role.Button,
                ),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (uiState.reportCycle) {
            ReportCycle.DAILY -> {
                if (uiState.dailyReport == null) {
                    EmptyReportContent(
                        modifier = Modifier.fillMaxSize(),
                        title = "오늘 퀴즈를 안 풀었어요",
                        description = "일일 결과는 일일 퀴즈를 모두 풀어야\n확인이 가능해요",
                    )
                } else {
                    val scrollState = rememberScrollState()
                    DailyReportContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.dp),
                        dailyQuizScore = uiState.dailyReport.dailyQuizScore,
                        advices = uiState.dailyReport.advices,
                    )
                }
            }

            ReportCycle.WEEKLY -> {
                if (uiState.weeklyReport == null) {
                    EmptyReportContent(
                        modifier = Modifier.fillMaxSize(),
                        title = "이번 주 퀴즈를 안 풀었어요",
                        description = "주간 결과는 일주일 동안 퀴즈를 모두 풀어야\n확인이 가능해요",
                    )
                } else {
                    val scrollState = rememberScrollState()
                    WeeklyReportContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.dp),
                        weeklyReport = uiState.weeklyReport,
                        onCategorySelected = onWeeklyReportCategorySelected,
                    )
                }
            }

            ReportCycle.MONTHLY -> {
                EmptyReportContent(
                    modifier = Modifier.fillMaxSize(),
                    title = "이번 달 퀴즈를 안 풀었어요",
                    description = "월간 동안의 결과는 한 달 동안 퀴즈를 모두 풀어야\n확인이 가능해요",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportContentPreview() {
    ReportContent(
        uiState = ReportUiState.INIT,
        onReportCycleChange = {},
        onPrevClick = {},
        onNextClick = {},
        onWeeklyReportCategorySelected = {},
        onBackClick = {},
    )
}
