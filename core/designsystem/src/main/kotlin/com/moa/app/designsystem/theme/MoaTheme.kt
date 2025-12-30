package com.moa.app.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.moa.app.designsystem.foundation.LocalMoaColors
import com.moa.app.designsystem.foundation.MoaColors
import com.moa.app.designsystem.foundation.LocalMoaTypography
import com.moa.app.designsystem.foundation.MoaTypography

object MoaTheme {
    val colors: MoaColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMoaColors.current

    val typography: MoaTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalMoaTypography.current
}

@Composable
fun MoATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalMoaColors provides LocalMoaColors.current,
        LocalMoaTypography provides LocalMoaTypography.current,
        content = content,
    )
}
