package com.kjurkovic.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalColors provides LightThemeColors) {
        MaterialTheme(
            colors = LocalColors.current.material,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}
