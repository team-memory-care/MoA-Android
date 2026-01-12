package com.moa.app.feature.senior.quiz.memory.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaButtonColors
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.quiz.component.TopQuizDescription
import com.moa.app.ui.extension.clickableWithoutRipple

@Composable
fun MemoryQuizVoiceModeContent(
    isSpeaking: Boolean,
    showChangeModeButton: Boolean,
    onImageClick: () -> Unit,
    onStartSpeakingClick: () -> Unit,
    onUnableToSpeakClick: () -> Unit,
    onChangeModeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopQuizDescription(
            quizDescription = "방금 나온 단어를\n순서대로 말씀해주세요!",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickableWithoutRipple(onClick = onImageClick, role = Role.Button)
        )

        VoiceInputButton(
            isSpeaking = isSpeaking,
            onClick = onStartSpeakingClick,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 8.dp)
        )

        MaButton(
            onClick = onUnableToSpeakClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = MaButtonColors(
                defaultBackground = Color.Transparent,
                pressedBackground = Color.Transparent,
                disabledBackground = Color.Transparent,
                defaultContentColor = MoaTheme.colors.coolGray90,
                pressedContentColor = MoaTheme.colors.coolGray90,
                disabledContentColor = MoaTheme.colors.coolGray90,
            ),
        ) {
            Text(
                text = "지금은 말할 수 없어요",
                style = MoaTheme.typography.body1Medium,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (showChangeModeButton) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MoaTheme.colors.green50)
                    .padding(top = 20.dp, bottom = 12.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "지금 말씀할 상황이 아니군요!",
                    color = MoaTheme.colors.green500,
                    style = MoaTheme.typography.title2Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "말하기를 단어쓰기로 대체할게요",
                    color = MoaTheme.colors.green900,
                    style = MoaTheme.typography.body2Medium,
                )

                Spacer(modifier = Modifier.height(20.dp))

                MaButton(
                    onClick = onChangeModeClick,
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "계속",
                        style = MoaTheme.typography.body1Bold,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun VoiceInputButton(
    isSpeaking: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(isSpeaking) {
        if (isSpeaking) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
            )
        }
    }

    val title = if (isSpeaking) "지금 말씀하세요!" else "여기를 누르고 말씀하세요!"
    val borderColor = if (isSpeaking) MoaTheme.colors.green500 else MoaTheme.colors.coolGray97
    val contentColor = if (isSpeaking) MoaTheme.colors.green500 else MoaTheme.colors.black
    val fillColor = MoaTheme.colors.green50

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .drawBehind {
                if (isSpeaking) {
                    drawRect(
                        color = fillColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(width = size.width * progress.value, height = size.height)
                    )
                }
            }
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(12.dp),
                color = borderColor
            )
            .clickable(enabled = !isSpeaking) { onClick() }
            .defaultMinSize(minWidth = 264.dp)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_mic),
            contentDescription = null,
            colorFilter = ColorFilter.tint(contentColor),
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = title,
            color = contentColor,
            style = MoaTheme.typography.body1Semibold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMemoryQuizVoiceModeContent() {
    MemoryQuizVoiceModeContent(
        isSpeaking = true,
        showChangeModeButton = true,
        onImageClick = {},
        onStartSpeakingClick = {},
        onUnableToSpeakClick = {},
        onChangeModeClick = {},
    )
}

@Preview
@Composable
private fun PreviewVoiceInputButton() {
    VoiceInputButton(
        isSpeaking = true,
        onClick = {},
    )
}
