package com.moa.app.feature.senior.quiz.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moa.app.domain.quiz.model.Quiz

@Composable
fun <T : Quiz> QuizSlideAnimation(
    targetState: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = targetState,
        label = QUIZ_SLIDE_ANIMATION,
        contentKey = { quiz -> quiz.id },
        transitionSpec = {
            (slideIntoContainer(
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
            ) togetherWith slideOutOfContainer(
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
            )).using(
                sizeTransform = SizeTransform(clip = true)
            )
        },
    ) { state ->
        content(state)
    }
}

@Composable
fun <T> CommonSideAnimation(
    targetState: T,
    modifier: Modifier = Modifier,
    contentKey: (T) -> Any = { it as Any },
    content: @Composable (T) -> Unit,
) {
    AnimatedContent(
        targetState = targetState,
        label = COMMON_SIDE_ANIMATION,
        contentKey = contentKey,
        transitionSpec = {
            (slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 300)
            ) togetherWith slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 300)
            )).using(
                sizeTransform = SizeTransform { _, _ ->
                    tween(durationMillis = 0)
                }
            )
        },
        modifier = modifier
    ) { state ->
        content(state)
    }
}

private const val COMMON_SIDE_ANIMATION = "CommonSideAnimation"
private const val QUIZ_SLIDE_ANIMATION = "QuizSlideAnimation"
