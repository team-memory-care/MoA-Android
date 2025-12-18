package com.moa.app.feature.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ReportScreen() {
    ReportContent()
}

@Composable
private fun ReportContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("여긴 리포트")
    }
}

@Preview
@Composable
private fun ReportContentPreview() {
    ReportContent()
}
