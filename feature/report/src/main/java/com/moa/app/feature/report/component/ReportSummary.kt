package com.moa.app.feature.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun ReportSummary(
    summary: String,
    completeRate: Int,
    correctRate: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = summary,
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.title2Bold,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MoaTheme.colors.coolGray99)
                    .padding(vertical = 24.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "$completeRate%",
                    color = MoaTheme.colors.black,
                    style = MoaTheme.typography.title2Bold,
                    modifier = Modifier.padding(bottom = 2.dp),
                )

                Text(
                    text = "완료율",
                    color = MoaTheme.colors.coolGray40,
                    style = MoaTheme.typography.body2Semibold,
                )
            }

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MoaTheme.colors.coolGray99)
                    .padding(vertical = 24.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "$correctRate%",
                    color = MoaTheme.colors.black,
                    style = MoaTheme.typography.title2Bold,
                    modifier = Modifier.padding(bottom = 2.dp),
                )

                Text(
                    text = "정답률",
                    color = MoaTheme.colors.coolGray40,
                    style = MoaTheme.typography.body2Semibold,
                )
            }
        }
    }
}

@Composable
fun ReportDetail(
    detailContent: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MoaTheme.colors.coolGray99)
            .padding(20.dp),
    ) {
        Text(
            text = detailContent,
            color = MoaTheme.colors.coolGray40,
            style = MoaTheme.typography.body2Medium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewReportSummary() {
    ReportSummary(
        summary = "이번 주에는 참여율은 꾸준했지만,\n정답률이 지난주보다 조금 떨어졌습니다.",
        completeRate = 80,
        correctRate = 50,
    )
}

@Preview
@Composable
private fun PreviewReportDetail() {
    ReportDetail(
        detailContent = "기억력은 안정적으로 유지되었지만, 주의력과 언어 영역에서 점진적인 하락이 관찰되었습니다.\n\n" +
            "특히 3주차에 언어 정답률이 크게 떨어지며, 월간 추세 전반에 영향을 준 것으로 보입니다.\n\n" +
            "응답 속도도 점차 늦어져, 단순 변동이 아닌 장기적인 인지 처리 부담이 누적되는 양상이 확인됩니다.",
    )
}
