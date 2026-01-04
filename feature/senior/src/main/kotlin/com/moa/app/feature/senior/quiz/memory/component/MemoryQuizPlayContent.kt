package com.moa.app.feature.senior.quiz.memory.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.moa.app.feature.senior.quiz.component.CommonSideAnimation
import kotlinx.coroutines.delay

@Composable
fun MemoryQuizPlayContent(
    imageUrls: List<String>,
    onImagesFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentImageIndex by remember { mutableIntStateOf(0) }
    val displayTimeMillis = 1200L
    val offsetStep = 12.dp

    val remainingCount = (imageUrls.size - 1 - currentImageIndex).coerceAtLeast(0)
    val totalStackWidth = offsetStep * remainingCount

    val centeringOffset by animateDpAsState(
        targetValue = -(totalStackWidth / 2),
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "CenteringOffset",
    )

    LaunchedEffect(currentImageIndex) {
        delay(displayTimeMillis)
        if (currentImageIndex < imageUrls.size - 1) {
            currentImageIndex++
        } else {
            delay(500)
            onImagesFinished()
        }
    }

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
            imageUrls.forEachIndexed { index, url ->
                if (index > currentImageIndex) {
                    val distance = index - currentImageIndex
                    val animatedOffset by animateDpAsState(
                        targetValue = offsetStep * distance,
                        animationSpec = tween(600, easing = FastOutSlowInEasing),
                        label = "BackCardOffset",
                    )

                    AsyncImage(
                        model = url,
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

            CommonSideAnimation(
                targetState = currentImageIndex,
                modifier = Modifier.zIndex(imageUrls.size.toFloat())
            ) { index ->
                AsyncImage(
                    model = imageUrls[index],
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
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
        onImagesFinished = {},
    )
}
