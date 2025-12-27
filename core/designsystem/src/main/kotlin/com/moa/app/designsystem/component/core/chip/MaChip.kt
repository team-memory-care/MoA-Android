package com.moa.app.designsystem.component.core.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun MaBasicChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean = false,
    colors: MaBasicChipColors = MaBasicChipDefaults.maBasicChipColors(),
    shape: Shape = MaBasicChipDefaults.shape,
    content: @Composable () -> Unit,
) {
    val backgroundColor = if (!selected) colors.defaultBackground else colors.selectedBackgroundColor
    val contentColor = if (!selected) colors.defaultContentColor else colors.selectedContentColor

    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
                role = Role.Button,
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

object MaBasicChipDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(99.dp)

    @Composable
    fun maBasicChipColors(
        defaultBackground: Color = MoaTheme.colors.coolGray98,
        selectedBackgroundColor: Color = MoaTheme.colors.green500,
        defaultContentColor: Color = MoaTheme.colors.coolGray90,
        selectedContentColor: Color = MoaTheme.colors.white,
    ): MaBasicChipColors =
        MaBasicChipColors(
            defaultBackground = defaultBackground,
            selectedBackgroundColor = selectedBackgroundColor,
            defaultContentColor = defaultContentColor,
            selectedContentColor = selectedContentColor
        )
}

@Immutable
data class MaBasicChipColors(
    val defaultBackground: Color,
    val selectedBackgroundColor: Color,
    val defaultContentColor: Color,
    val selectedContentColor: Color,
)

@Preview
@Composable
private fun PreviewMaBasicChip() {
    MaBasicChip(
        selected = true,
        onClick = {},
    ) {
        Text(
            text = "Label",
            style = MoaTheme.typography.body1Bold,
            modifier = Modifier.padding(10.dp),
        )
    }
}
