package com.moa.app.ui.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = this.then(
    Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = role,
        onClick = onClick,
    )
)
