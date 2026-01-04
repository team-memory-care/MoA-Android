package com.moa.app.feature.senior.quiz.memory.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.R

@Composable
fun MemoryQuizReadyContent(
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MemoryQuizInstructionCard(
            modifier = Modifier.padding(horizontal = 20.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        MaButton(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
        ) {
            Text(
                text = "계속",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            )
        }
    }
}

@Composable
private fun MemoryQuizInstructionCard(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box {
            CardStackBackground(
                modifier = Modifier.matchParentSize(),
                stackCount = 4,
                offsetStep = 8.dp,
            )

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(MoaTheme.colors.lightBlue100)
                    .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_quiz_load_character),
                    contentDescription = null,
                    modifier = Modifier.offset(y = 4.dp),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(MoaTheme.colors.white)
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "그림 3개를 모두 본 후\n순서대로 말씀하시면 돼요",
                        color = MoaTheme.colors.black,
                        style = MoaTheme.typography.title2Semibold,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "준비되면 하단의 버튼을 눌러주세요.",
                        color = MoaTheme.colors.coolGray80,
                        style = MoaTheme.typography.body1Regular,
                    )
                }
            }
        }
    }
}

@Composable
fun CardStackBackground(
    modifier: Modifier = Modifier,
    stackCount: Int = 3,
    offsetStep: Dp = 8.dp,
) {
    Box(modifier = modifier) {
        for (i in (stackCount - 1) downTo 1) {
            val isLastCard = (i == stackCount - 1)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = (offsetStep * i), y = 0.dp)
                    .background(
                        color = MoaTheme.colors.lightBlue700,
                        shape = RoundedCornerShape(32.dp),
                    )
                    .border(
                        width = if (!isLastCard) 2.dp else 1.dp,
                        color = MoaTheme.colors.lightBlue100,
                        shape = RoundedCornerShape(32.dp),
                    ),
                )
        }
    }
}

@Preview
@Composable
private fun PreviewMemoryQuizReadyContent() {
    MemoryQuizReadyContent(
        onContinueClick = {},
    )
}
