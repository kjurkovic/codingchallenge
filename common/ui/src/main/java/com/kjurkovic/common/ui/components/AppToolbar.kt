package com.kjurkovic.common.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kjurkovic.common.ui.R

@Composable
fun AppToolbar(
    titleContent: @Composable (BoxScope.() -> Unit)? = null,
    leftContent: @Composable (BoxScope.() -> Unit)? = null,
    rightContent: @Composable (BoxScope.() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.height_toolbar))
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            leftContent?.invoke(this)
        }

        Box(modifier = Modifier.align(Alignment.Center)) {
            titleContent?.invoke(this)
        }

        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            rightContent?.invoke(this)
        }
    }
}