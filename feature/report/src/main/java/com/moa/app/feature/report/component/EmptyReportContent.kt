package com.moa.app.feature.report.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun EmptyReportContent(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.title2Bold,
        )
        Text(
            text = description,
            color = MoaTheme.colors.coolGray60,
            style = MoaTheme.typography.caption1Medium,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun PreviewEmptyReportContent() {
    EmptyReportContent(
        title = "오늘 퀴즈를 안 풀었어요",
        description = "일일 결과는 일일 퀴즈를 모두 풀어야\n확인이 가능해요",
    )
}
