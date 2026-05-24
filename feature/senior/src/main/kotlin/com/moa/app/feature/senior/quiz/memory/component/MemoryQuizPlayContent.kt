package com.moa.app.feature.senior.quiz.memory.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.moa.app.designsystem.R
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.internal.rememberQuizImageRequest

@Composable
fun MemoryQuizPlayContent(
    imageUrls: List<String>,
    currentImageIndex: Int,
    modifier: Modifier = Modifier,
) {
    if (imageUrls.isEmpty()) return

    val coercedImageIndex = currentImageIndex.coerceIn(0, imageUrls.lastIndex)
    val offsetStep = 12.dp

    val remainingCount = (imageUrls.size - 1 - coercedImageIndex).coerceAtLeast(0)
    val totalStackWidth = offsetStep * remainingCount

    val centeringOffset by animateDpAsState(
        targetValue = -(totalStackWidth / 2),
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "CenteringOffset",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier.offset(x = centeringOffset),
            contentAlignment = Alignment.Center,
        ) {
            imageUrls.forEachIndexed { index, _ ->
                if (index > coercedImageIndex) {
                    val distance = index - coercedImageIndex
                    val animatedOffset by animateDpAsState(
                        targetValue = offsetStep * distance,
                        animationSpec = tween(600, easing = FastOutSlowInEasing),
                        label = "BackCardOffset",
                    )

                    Image(
                        painter = painterResource(R.drawable.memory_image_place_holder),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = animatedOffset)
                            .zIndex(imageUrls.size.toFloat() - index)
                            .graphicsLayer { alpha = 0.9f },
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }

            MemoryCardAnimation(
                targetImageIndex = coercedImageIndex,
                modifier = Modifier.zIndex(imageUrls.size.toFloat()),
            ) { index ->
                AsyncImage(
                    model = rememberQuizImageRequest(imageUrls[index], QuizCategory.MEMORY),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}

@Composable
private fun MemoryCardAnimation(
    targetImageIndex: Int,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit,
) {
    AnimatedContent(
        targetState = targetImageIndex,
        transitionSpec = {
            val enterTransition = fadeIn(
                animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            )

            val exitTransition = slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            ) + fadeOut(animationSpec = tween(durationMillis = 300))

            (enterTransition togetherWith exitTransition).using(
                SizeTransform(clip = false) { _, _ -> tween(0) },
            ).apply {
                targetContentZIndex = -1f
            }
        },
        modifier = modifier,
        label = "MemoryCardAnimation",
    ) { index ->
        content(index)
    }
}

@Preview
@Composable
private fun Preview() {
    MemoryQuizPlayContent(
        imageUrls = listOf(
            "https://moa-bucket-s3.s3.ap-northeast-2.amazonaws.com/8d120aaa-0_memory_2.png",
            "https://moa-bucket-s3.s3.ap-northeast-2.amazonaws.com/03548c44-e_memory_4.png",
            "https://moa-bucket-s3.s3.ap-northeast-2.amazonaws.com/37300c63-c_memory_8.png",
        ),
        currentImageIndex = 0,
    )
}
