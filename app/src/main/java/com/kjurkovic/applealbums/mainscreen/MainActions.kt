package com.kjurkovic.applealbums.mainscreen

import com.kjurkovic.common.framework.navigation.BaseNavigationFactory

sealed class MainActions {

}

data class MainViewState(
    val startDestination: String,
    val navigationFactories: Set<BaseNavigationFactory>,
)