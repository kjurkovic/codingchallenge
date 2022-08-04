package com.kjurkovic.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kjurkovic.common.ui.theme.allColors

@Composable
fun AppScreen(
    backgroundColor: Color = MaterialTheme.allColors.background,
    topBarContent: (@Composable () -> Unit)? = null,
    topBarBackgroundColor: Color = backgroundColor,
    topBarElevation: Dp = 0.dp,
    statusBarColor: Color? = null,
    statusBarDarkIcons: Boolean = when {
        statusBarColor != null -> statusBarColor
        topBarContent != null -> topBarBackgroundColor
        else -> backgroundColor
    }.luminance() > 0.5f,
    content: @Composable (PaddingValues) -> Unit,
) {
    // rememberSystemUiController does not work in Preview
    if (!LocalInspectionMode.current) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            statusBarDarkIcons.let {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = statusBarDarkIcons,
                )
            }
        }
    }

    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) {

        val statusBarInsets = WindowInsets.statusBars
        val statusBarHeight = remember(statusBarInsets) {
            density.run { statusBarInsets.getTop(density).toDp() }
        }

        val navigationBarInsets = WindowInsets.navigationBars
        val navigationBarHeight = remember(navigationBarInsets) {
            density.run { navigationBarInsets.getBottom(density).toDp() }
        }

        val topPadding = remember { mutableStateOf(statusBarHeight) }
        val bottomPadding = remember { mutableStateOf(navigationBarHeight) }
        val innerPadding = remember(topPadding.value) {
            PaddingValues(top = topPadding.value, bottom = bottomPadding.value)
        }

        content(innerPadding)

        topBarContent?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onGloballyPositioned { coordinates ->
                        topPadding.value = density.run { coordinates.size.height.toDp() }
                    }
                    .graphicsLayer {
                        shadowElevation = topBarElevation.toPx()
                    }
                    .background(topBarBackgroundColor)
                    .padding(top = statusBarHeight)
            ) {
                topBarContent()
            }
        }
        statusBarColor?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(statusBarHeight)
                    .background(statusBarColor)
            )
        }
    }
}
