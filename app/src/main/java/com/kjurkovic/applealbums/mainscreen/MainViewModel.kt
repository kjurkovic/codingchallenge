package com.kjurkovic.applealbums.mainscreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kjurkovic.common.framework.navigation.AppDestination
import com.kjurkovic.common.framework.navigation.AppNavigator
import com.kjurkovic.common.framework.navigation.BaseNavigationFactory
import com.kjurkovic.common.mvvm.BaseViewModel
import com.kjurkovic.common.wrappers.dispatchers.DispatcherProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class MainViewModel @AssistedInject constructor(
    dispatcherProvider: DispatcherProvider,
    navigator: AppNavigator,
    navigationFactories: @JvmSuppressWildcards Set<BaseNavigationFactory>,
) : BaseViewModel<MainViewState, MainActions, Nothing>(
    dispatcherProvider = dispatcherProvider,
    viewState = MainViewState(
        startDestination = AppDestination.Albums.fullRoute,
        navigationFactories = navigationFactories,
    ),
) {
    internal val navigationChannel = navigator.navigationChannel

    @AssistedFactory
    interface Factory {
        fun create(): MainViewModel
    }

    companion object {
        @Composable
        fun create(assistedFactory: Factory): MainViewModel =
            viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create() as T
                }
            })
    }

    override suspend fun reduce(action: MainActions) {

    }
}