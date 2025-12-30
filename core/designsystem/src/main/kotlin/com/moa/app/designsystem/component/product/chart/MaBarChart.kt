package com.moa.app.designsystem.component.product.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MaBarChart(
    items: ImmutableList<BarChartItem>,
    modifier: Modifier = Modifier,
    maxRange: Float = 100f
) {
    val textMeasurer = rememberTextMeasurer()
    val labelTextStyle = MoaTheme.typography.caption1Regular.copy(
        color = MoaTheme.colors.coolGray90,
    )
    val colorPrimary = MoaTheme.colors.green400
    val colorSecondary = MoaTheme.colors.blue200
    val gridColor = MoaTheme.colors.coolGray97

    val barPath = remember { Path() }

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val bottomPadding = 40.dp.toPx()
        val leftPadding = 40.dp.toPx()
        val cornerRadiusPx = 8.dp.toPx() // 모서리 반경

        val chartHeight = canvasHeight - bottomPadding
        val chartWidth = canvasWidth - leftPadding

        val step = (maxRange / 5).toInt()
        for (i in 0..maxRange.toInt() step step) {
            val yRatio = i / maxRange
            val yPos = chartHeight - (yRatio * chartHeight)

            drawText(
                textMeasurer = textMeasurer,
                text = i.toString(),
                topLeft = Offset(0f, yPos - 10.dp.toPx()),
                style = labelTextStyle
            )

            drawLine(
                color = gridColor,
                start = Offset(leftPadding, yPos),
                end = Offset(canvasWidth, yPos),
                strokeWidth = 1.dp.toPx()
            )
        }

        if (items.isNotEmpty()) {
            val barWidth = (chartWidth / items.size) / 3.5f
            val groupSpacing = chartWidth / items.size

            items.forEachIndexed { index, item ->
                val xOffset = leftPadding + (index * groupSpacing) + (groupSpacing / 2)

                val barHeightPrimary = (item.primaryValue / maxRange) * chartHeight
                val primaryRect = Rect(
                    offset = Offset(xOffset - barWidth - 4f, chartHeight - barHeightPrimary),
                    size = Size(barWidth, barHeightPrimary)
                )

                barPath.rewind()
                barPath.addRoundRect(
                    RoundRect(
                        rect = primaryRect,
                        topLeft = CornerRadius(cornerRadiusPx),
                        topRight = CornerRadius(cornerRadiusPx),
                        bottomLeft = CornerRadius.Zero,
                        bottomRight = CornerRadius.Zero
                    )
                )
                drawPath(path = barPath, color = colorPrimary)

                val barHeightSecondary = (item.secondaryValue / maxRange) * chartHeight
                val secondaryRect = Rect(
                    offset = Offset(xOffset + 4f, chartHeight - barHeightSecondary),
                    size = Size(barWidth, barHeightSecondary)
                )

                barPath.rewind()
                barPath.addRoundRect(
                    RoundRect(
                        rect = secondaryRect,
                        topLeft = CornerRadius(cornerRadiusPx),
                        topRight = CornerRadius(cornerRadiusPx),
                        bottomLeft = CornerRadius.Zero,
                        bottomRight = CornerRadius.Zero
                    )
                )
                drawPath(path = barPath, color = colorSecondary)

                val textLayoutResult = textMeasurer.measure(item.label, labelTextStyle)
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        xOffset - (textLayoutResult.size.width / 2),
                        chartHeight + 10.dp.toPx()
                    )
                )
            }
        }
    }
}

@Composable
fun ChartLegendItems(
    currentText: String,
    lastText: String,
    modifier: Modifier = Modifier
) {
    val currentColor = MoaTheme.colors.green400
    val lastColor = MoaTheme.colors.blue200

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(10.dp),
            onDraw = { drawCircle(currentColor) }
        )

        Text(
            text = currentText,
            color = MoaTheme.colors.coolGray90,
            style = MoaTheme.typography.caption2Semibold
        )

        Spacer(modifier = Modifier.width(20.dp))

        Canvas(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(10.dp),
            onDraw = { drawCircle(lastColor) }
        )

        Text(
            text = lastText,
            color = MoaTheme.colors.coolGray90,
            style = MoaTheme.typography.caption2Semibold
        )
    }
}

data class BarChartItem(
    val label: String,
    val primaryValue: Long,
    val secondaryValue: Long,
)

@Preview
@Composable
private fun Preview() {
    MaBarChart(
        items = persistentListOf(
            BarChartItem("월", 10, 30),
            BarChartItem("화", 10, 60),
            BarChartItem("수", 10, 30),
            BarChartItem("목", 10, 60),
            BarChartItem("금", 10, 30),
            BarChartItem("토", 10, 60),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}
