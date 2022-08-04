package com.kjurkovic.common.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.kjurkovic.common.ui.R

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            CircularProgressIndicator()
            Text(
                text = stringResource(R.string.please_wait),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.default_margin))
            )
        }
    }
}
