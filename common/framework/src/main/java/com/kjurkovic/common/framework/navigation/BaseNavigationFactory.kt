package com.kjurkovic.common.framework.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface BaseNavigationFactory {
    fun create(builder: NavGraphBuilder, navController: NavController)
}