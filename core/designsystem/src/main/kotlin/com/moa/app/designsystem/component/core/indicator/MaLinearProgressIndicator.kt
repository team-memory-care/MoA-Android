package com.moa.app.designsystem.component.core.indicator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun MaLinearProgressIndicator(
    correct: Int,
    total: Int,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(50)
) {
    val progressRatio = if (total > 0) correct.toFloat() / total.toFloat() else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progressRatio.coerceIn(0f, 1f),
        label = "ProgressAnimation"
    )

    Box(
        modifier = modifier
            .height(12.dp)
            .clip(shape)
            .background(MoaTheme.colors.green100)
            .semantics {
                progressBarRangeInfo = ProgressBarRangeInfo(
                    current = progressRatio,
                    range = 0f..1f
                )
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(shape)
                .background(MoaTheme.colors.green500)
        )
    }
}

@Preview
@Composable
private fun PreviewMaLinearProgressIndicator() {
    MaLinearProgressIndicator(
        correct = 5,
        total = 10,
        modifier = Modifier.fillMaxWidth()
    )
}
