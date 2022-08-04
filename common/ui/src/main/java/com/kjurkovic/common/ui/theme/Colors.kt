package com.kjurkovic.common.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF4D4D4D)
val White = Color(0xFFFFFFFF)
val Gray = Color(0xFFB5B5B5)
val TransparentWhite = Color(0x80FFFFFF)

val PrimaryColor = Color(0xFF007AFF)
val PrimaryDarkColor = Color(0xFF0062CE)

val Error = Color(0xFFFF0000)

val ImageOverlayGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0x00000000),
        Color(0xc0000000),
    )
)