package com.moa.app.designsystem.component.core.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
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
fun MaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaButtonDefaults.shape,
    colors: MaButtonColors = MaButtonDefaults.maButtonColors(),
    content: @Composable BoxScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = when {
        !enabled -> colors.disabledBackground
        isPressed -> colors.pressedBackground
        else -> colors.defaultBackground
    }
    val contentColor = when {
        !enabled -> colors.disabledContentColor
        isPressed -> colors.pressedContentColor
        else -> colors.defaultContentColor
    }

    Box(
        modifier = modifier
            .semantics { role = Role.Button }
            .background(color = backgroundColor, shape = shape)
            .clickable(
                enabled = enabled,
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

object MaButtonDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(12.dp)

    @Composable
    fun maButtonColors(): MaButtonColors =
        MaButtonColors(
            defaultBackground = MoaTheme.colors.green500,
            pressedBackground = MoaTheme.colors.green600,
            disabledBackground = MoaTheme.colors.green100,
            defaultContentColor = MoaTheme.colors.white,
            pressedContentColor = MoaTheme.colors.white,
            disabledContentColor = MoaTheme.colors.white,
        )

    @Composable
    fun maSelectButtonColors(): MaSelectButtonColors =
        MaSelectButtonColors(
            selectedBackground = MoaTheme.colors.green50,
            selectedBorderColor = MoaTheme.colors.green500,
            selectedContentColor = MoaTheme.colors.green700,
            unselectedBackground = MoaTheme.colors.coolGray98,
            unselectedBorderColor = MoaTheme.colors.coolGray98,
            unselectedContentColor = MoaTheme.colors.coolGray60,
        )
}

@Immutable
data class MaButtonColors(
    val defaultBackground: Color,
    val pressedBackground: Color,
    val disabledBackground: Color,
    val defaultContentColor: Color,
    val pressedContentColor: Color,
    val disabledContentColor: Color,
)

@Preview
@Composable
private fun Preview() {
    MaButton(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Label",
            style = MoaTheme.typography.body1Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )
    }
}
