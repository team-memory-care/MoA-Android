package com.moa.app.feature.senior.quiz.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        label = CARD_TOSS_ANIMATION,
        contentKey = contentKey,
        transitionSpec = {
            val enterTransition = fadeIn(
                animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
            )

            val exitTransition = slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(durationMillis = 300))

            (enterTransition togetherWith exitTransition).using(
                SizeTransform(clip = false) { _, _ -> tween(0) }
            ).apply {
                targetContentZIndex = -1f
            }
        },
        modifier = modifier
    ) { state ->
        content(state)
    }
}

private const val CARD_TOSS_ANIMATION = "CardTossAnimation"
private const val QUIZ_SLIDE_ANIMATION = "QuizSlideAnimation"
