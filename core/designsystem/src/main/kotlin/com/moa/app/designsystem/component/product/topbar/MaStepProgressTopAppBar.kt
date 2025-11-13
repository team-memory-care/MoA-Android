package com.moa.app.designsystem.component.product.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.indicator.StepIndicator
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun MaStepProgressTopAppBar(
    title: String,
    totalSteps: Int,
    currentStep: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
            .padding(vertical = 12.dp),
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_left),
            contentDescription = "뒤로가기",
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable(
                    onClick = onBackClick,
                    role = Role.Button,
                    interactionSource = null,
                    indication = null,
                )
                .padding(12.dp),
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(184.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = MoaTheme.typography.headLine2Bold,
                color = MoaTheme.colors.black,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            StepIndicator(
                totalSteps = totalSteps,
                currentStep = currentStep,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column {
        MaStepProgressTopAppBar(
            title = "지남력 퀴즈",
            onBackClick = {},
            totalSteps = 5,
            currentStep = 2
        )
    }
}
