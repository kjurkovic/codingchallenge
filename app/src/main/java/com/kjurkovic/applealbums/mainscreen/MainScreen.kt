package com.kjurkovic.applealbums.mainscreen

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kjurkovic.common.framework.navigation.NavigationAction
import com.kjurkovic.common.ui.theme.AppTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val navController = rememberNavController()
    val viewState = viewModel.viewState.collectAsState().value

    MainNavigationActions(viewModel.navigationChannel, navController)

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = viewState.startDestination,
        ) {
            viewState.navigationFactories.forEach { factory ->
                factory.create(this, navController)
            }
        }
    }
}

@Composable
private fun MainNavigationActions(
    navigationChannel: Channel<NavigationAction>,
    navController: NavHostController,
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationAction.NavigateBack -> {
                    if (intent.route != null) {
                        navController.popBackStack(intent.route!!, intent.inclusive)
                    } else {
                        navController.popBackStack()
                    }
                }
                is NavigationAction.NavigateTo -> {
                    navController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}