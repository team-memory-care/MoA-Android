package com.moa.app.designsystem.component.core.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun MaQuizButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: QuizButtonState = QuizButtonState.DEFAULT,
    shape: Shape = MaQuizButtonDefaults.shape,
    colors: MaQuizButtonColors = MaQuizButtonDefaults.maQuizButtonColors(),
    borderColors: MaQuizButtonBorderColors = MaQuizButtonDefaults.maQuizButtonBorderColors(),
    content: @Composable BoxScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = when {
        isPressed -> colors.pressedBackgroundColor
        state == QuizButtonState.SELECTED -> colors.selectedBackgroundColor
        state == QuizButtonState.UNSELECTED -> colors.notSelectedBackgroundColor
        else -> colors.defaultBackgroundColor
    }
    val contentColor = when {
        isPressed -> colors.pressedContentColor
        state == QuizButtonState.SELECTED -> colors.selectedContentColor
        state == QuizButtonState.UNSELECTED -> colors.notSelectedContentColor
        else -> colors.defaultContentColor
    }

    val borderColor = when {
        isPressed -> borderColors.pressedBorderColor
        state == QuizButtonState.SELECTED -> borderColors.selectedBorderColor
        state == QuizButtonState.UNSELECTED -> borderColors.notSelectedBorderColor
        else -> borderColors.defaultBorderColor
    }

    Box(
        modifier = modifier
            .semantics { role = Role.Button }
            .background(
                color = backgroundColor,
                shape = shape,
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = shape,
            )
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

object MaQuizButtonDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(12.dp)

    @Composable
    fun maQuizButtonColors(): MaQuizButtonColors =
        MaQuizButtonColors(
            defaultBackgroundColor = MoaTheme.colors.white,
            pressedBackgroundColor = MoaTheme.colors.coolGray98,
            selectedBackgroundColor = MoaTheme.colors.green50,
            notSelectedBackgroundColor = MoaTheme.colors.coolGray98,
            defaultContentColor = MoaTheme.colors.black,
            pressedContentColor = MoaTheme.colors.black,
            selectedContentColor = MoaTheme.colors.green500,
            notSelectedContentColor = MoaTheme.colors.coolGray90
        )

    @Composable
    fun maQuizButtonBorderColors(): MaQuizButtonBorderColors =
        MaQuizButtonBorderColors(
            defaultBorderColor = MoaTheme.colors.coolGray97,
            pressedBorderColor = MoaTheme.colors.coolGray97,
            selectedBorderColor = MoaTheme.colors.green300,
            notSelectedBorderColor = MoaTheme.colors.coolGray97,
        )
}

@Immutable
data class MaQuizButtonColors(
    val defaultBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val selectedBackgroundColor: Color,
    val notSelectedBackgroundColor: Color,
    val defaultContentColor: Color,
    val pressedContentColor: Color,
    val selectedContentColor: Color,
    val notSelectedContentColor: Color,
)

@Immutable
data class MaQuizButtonBorderColors(
    val defaultBorderColor: Color,
    val pressedBorderColor: Color,
    val selectedBorderColor: Color,
    val notSelectedBorderColor: Color,
)

enum class QuizButtonState {
    DEFAULT,
    SELECTED,
    UNSELECTED
}

@Preview(showBackground = true)
@Composable
private fun MaQuizButtonPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        MaQuizButton(
            onClick = {},
            state = QuizButtonState.DEFAULT,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Default State",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp),
            )
        }

        Spacer(Modifier.height(8.dp))

        MaQuizButton(
            onClick = {},
            state = QuizButtonState.SELECTED,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Selected State",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp),
            )
        }

        Spacer(Modifier.height(8.dp))

        MaQuizButton(
            onClick = {},
            state = QuizButtonState.UNSELECTED,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Unselected State",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp),
            )
        }
    }
}
