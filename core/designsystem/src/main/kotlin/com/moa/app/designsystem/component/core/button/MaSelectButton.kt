package com.moa.app.designsystem.component.core.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun MaSelectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    shape: Shape = MaButtonDefaults.shape,
    colors: MaSelectButtonColors = MaButtonDefaults.maSelectButtonColors(),
    content: @Composable BoxScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val backgroundColor = if (selected) colors.selectedBackground else colors.unselectedBackground
    val contentColor = if (selected) colors.selectedContentColor else colors.unselectedContentColor
    val borderColor = if (selected) colors.selectedBorderColor else colors.unselectedBorderColor

    Box(
        modifier = modifier
            .semantics { role = Role.Button }
            .background(color = backgroundColor, shape = shape)
            .border(
                width = 1.dp,
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

@Immutable
data class MaSelectButtonColors(
    val selectedBackground: Color,
    val selectedBorderColor: Color,
    val selectedContentColor: Color,
    val unselectedBackground: Color,
    val unselectedBorderColor: Color,
    val unselectedContentColor: Color,
)

@Preview
@Composable
private fun Preview() {
    var isSelected by remember { mutableStateOf(false) }

    MaSelectButton(
        onClick = { isSelected = !isSelected },
        selected = isSelected,
        modifier = Modifier
    ) {
        Text(
            text = "label",
            style = MoaTheme.typography.body1Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )
    }
}
