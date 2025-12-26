package com.moa.app.feature.report.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.indicator.MaLinerProgressIndicator
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.report.extension.scoreBackgroundColor
import com.moa.app.feature.report.extension.scoreIconRes
import com.moa.app.feature.report.extension.scoreTitle
import com.moa.app.feature.report.model.DailyQuizScoreUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DailyReportContent(
    dailyQuizScore: ImmutableList<DailyQuizScoreUiModel>,
    advices: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    val quizAll = remember(dailyQuizScore) {
        dailyQuizScore.find { it.type == QuizCategory.ALL }
    }
    val categoryScores = remember(dailyQuizScore) {
        dailyQuizScore.filter { it.type != QuizCategory.ALL }
    }

    Column(
        modifier = modifier.padding(bottom = 32.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = MoaTheme.colors.coolGray99)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "퀴즈 채점 결과",
                    color = MoaTheme.colors.black,
                    style = MoaTheme.typography.title2Bold,
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "${quizAll?.correctNumber}",
                        color = MoaTheme.colors.black,
                        style = MoaTheme.typography.headLine2Bold,
                    )
                    Text(
                        text = "/${quizAll?.totalNumber}",
                        color = MoaTheme.colors.black,
                        style = MoaTheme.typography.title2Medium,
                    )
                }
            }

            MaLinerProgressIndicator(
                correct = quizAll?.correctNumber ?: 0,
                total = quizAll?.totalNumber ?: 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "항목별 점수",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.title2Semibold,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(
                items = categoryScores,
                key = { it.type.name },
            ) { scoreItem ->
                ReportQuizScoreCard(dailyQuizScore = scoreItem)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "일상조언",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.title2Semibold,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        advices.forEachIndexed { index, advice ->
            Box(
                modifier = Modifier
                    .padding(bottom = if (index == advices.lastIndex) 0.dp else 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MoaTheme.colors.coolGray99)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    text = advice,
                    color = MoaTheme.colors.black,
                    style = MoaTheme.typography.body2Semibold,
                )
            }
        }
    }
}

@Composable
private fun ReportQuizScoreCard(
    dailyQuizScore: DailyQuizScoreUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(dailyQuizScore.type.scoreBackgroundColor)
            .width(214.dp),
        verticalArrangement = Arrangement.spacedBy(36.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = dailyQuizScore.type.scoreTitle,
                color = MoaTheme.colors.white,
                style = MoaTheme.typography.title2Semibold,
            )

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "${dailyQuizScore.correctNumber}점/",
                    color = MoaTheme.colors.white,
                    style = MoaTheme.typography.headLine2Bold,
                )
                Text(
                    text = "${dailyQuizScore.totalNumber}점",
                    color = MoaTheme.colors.white,
                    style = MoaTheme.typography.body1Medium,
                )
            }
        }

        Image(
            painter = painterResource(id = dailyQuizScore.type.scoreIconRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.End)
                .size(144.dp, 126.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyReportContentPreview() {
    DailyReportContent(
        dailyQuizScore = persistentListOf(
            DailyQuizScoreUiModel(QuizCategory.PERSISTENCE, 5, 10),
            DailyQuizScoreUiModel(QuizCategory.LINGUISTIC, 5, 10),
            DailyQuizScoreUiModel(QuizCategory.MEMORY, 5, 10),
            DailyQuizScoreUiModel(QuizCategory.ATTENTION, 5, 10),
            DailyQuizScoreUiModel(QuizCategory.SPACETIME, 5, 10),
            DailyQuizScoreUiModel(QuizCategory.ALL, 5, 10),
        ),
        advices = persistentListOf("일상조언1", "일상조언2", "일상조언3"),
    )
}
