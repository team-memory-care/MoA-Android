package com.moa.app.designsystem.component.core.indicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun StepIndicator(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    colors: StepIndicatorColors = StepIndicatorDefaults.colors(),
    sizes: StepIndicatorSizes = StepIndicatorDefaults.sizes(),
) {
    if (totalSteps <= 0) return
    val totalSegments = (totalSteps - 1).coerceAtLeast(0)
    val activeSegments = (currentStep - 1).coerceAtLeast(0).coerceAtMost(totalSegments)
    val fraction = if (totalSegments == 0) 0f else activeSegments.toFloat() / totalSegments.toFloat()

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 300),
        label = "LineFractionAnimation",
    )

    val animatedCircleColors = (1..totalSteps).map { stepIndex ->
        val targetColor =
            if (stepIndex <= currentStep) colors.activeColor else colors.inactiveColor

        animateColorAsState(
            targetValue = targetColor,
            animationSpec = tween(durationMillis = 300),
            label = "CircleColorAnimation"
        ).value
    }

    Canvas(
        modifier = modifier.height(sizes.circleSize)
    ) {
        val lineThicknessPx = sizes.lineThickness.toPx()
        val circleRadiusPx = sizes.circleSize.toPx() / 2f
        val yCenter = size.height / 2f
        val lineStartX = circleRadiusPx
        val lineEndX = size.width - circleRadiusPx
        val lineTrackWidth = lineEndX - lineStartX

        val circleCentersX = (0 until totalSteps).map { i ->
            if (totalSteps == 1) {
                size.width / 2f
            } else {
                val stepFraction = i.toFloat() / (totalSteps - 1)
                lineStartX + (lineTrackWidth * stepFraction)
            }
        }

        if (totalSteps > 1) {
            drawLine(
                color = colors.inactiveColor,
                start = Offset(lineStartX, yCenter),
                end = Offset(lineEndX, yCenter),
                strokeWidth = lineThicknessPx,
                cap = StrokeCap.Round
            )

            if (animatedFraction > 0f) {
                drawLine(
                    color = colors.activeColor,
                    start = Offset(lineStartX, yCenter),
                    end = Offset(lineStartX + (lineTrackWidth * animatedFraction), yCenter),
                    strokeWidth = lineThicknessPx,
                    cap = StrokeCap.Round
                )
            }
        }

        animatedCircleColors.forEachIndexed { index, color ->
            drawCircle(
                color = color,
                radius = circleRadiusPx,
                center = Offset(circleCentersX[index], yCenter)
            )
        }
    }
}

object StepIndicatorDefaults {
    @Composable
    fun colors(
        activeColor: Color = MoaTheme.colors.green500,
        inactiveColor: Color = MoaTheme.colors.coolGray97,
    ): StepIndicatorColors = StepIndicatorColors(
        activeColor = activeColor,
        inactiveColor = inactiveColor,
    )

    @Composable
    fun sizes(
        lineThickness: Dp = 4.dp,
        circleSize: Dp = 20.dp,
    ): StepIndicatorSizes = StepIndicatorSizes(
        lineThickness = lineThickness,
        circleSize = circleSize,
    )
}

@Immutable
data class StepIndicatorColors(val activeColor: Color, val inactiveColor: Color)

@Immutable
data class StepIndicatorSizes(val lineThickness: Dp, val circleSize: Dp)

@Preview
@Composable
private fun StepIndicatorPreview() {
    StepIndicator(
        totalSteps = 5,
        currentStep = 1,
        modifier = Modifier.fillMaxWidth()
    )
}
