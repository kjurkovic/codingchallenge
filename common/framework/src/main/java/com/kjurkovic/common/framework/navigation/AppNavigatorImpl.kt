package com.kjurkovic.common.framework.navigation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import timber.log.Timber

class AppNavigatorImpl : AppNavigator {

    override val navigationChannel = Channel<NavigationAction>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override suspend fun navigateBack(
        route: String?,
        inclusive: Boolean,
    ) = withContext(Dispatchers.Main) {
        Timber.d("navigateBack route=$route; inclusive=$inclusive")
        navigationChannel.send(NavigationAction.NavigateBack(
            route = route,
            inclusive = inclusive
        ))
    }

    override suspend fun navigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean,
    ) = withContext(Dispatchers.Main) {
        Timber.d("navigateTo route=$route; popUpToRoute=$popUpToRoute; inclusive=$inclusive; isSingleTop=$isSingleTop")
        navigationChannel.send(NavigationAction.NavigateTo(
            route = route,
            popUpToRoute = popUpToRoute,
            inclusive = inclusive,
            isSingleTop = isSingleTop,
        ))
    }
}
