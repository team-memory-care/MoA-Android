package com.moa.app.feature.senior.quiz.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.moa.app.designsystem.R
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun QuizCategoryCard(
    title: String,
    description: String,
    backgroundImage: Int,
    backgroundColor: Color,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(
                enabled = isEnabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick,
                role = Role.Button
            ),
    ) {
        Image(
            painter = painterResource(backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = if (isEnabled) 1f else 0.5f,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        )

        if (!isEnabled) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MoaTheme.colors.coolGray80)
            )
        }

        Text(
            text = title,
            color = MoaTheme.colors.white,
            style = MoaTheme.typography.title2Bold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp)
        )

        Text(
            text = description,
            color = MoaTheme.colors.white,
            style = MoaTheme.typography.body2Medium,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    QuizCategoryCard(
        title = "Title",
        description = "Description",
        backgroundImage = R.drawable.img_quiz_list_1,
        backgroundColor = MoaTheme.colors.blue400,
        isEnabled = true,
        modifier = Modifier.fillMaxWidth(),
        onClick = {}
    )
}
