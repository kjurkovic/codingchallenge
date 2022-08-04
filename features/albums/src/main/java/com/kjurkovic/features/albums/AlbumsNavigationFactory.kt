package com.kjurkovic.features.albums

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.kjurkovic.common.framework.navigation.*
import com.kjurkovic.features.albums.details.AlbumsDetailsScreen
import com.kjurkovic.features.albums.details.AlbumsDetailsViewModel
import com.kjurkovic.features.albums.main.AlbumsScreen
import com.kjurkovic.features.albums.main.AlbumsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject

@HiltNavigationFactory
class AlbumsNavigationFactory @Inject constructor(
    private val albumsViewModelFactory: AlbumsViewModel.Factory,
    private val albumDetailsViewModelFactory: AlbumsDetailsViewModel.Factory,
) : BaseNavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavController) {
        builder.navigation(
            route = AppDestination.Albums.fullRoute,
            startDestination = AppDestination.Albums.Main.fullRoute,
        ) {
            albumListScreen()
            albumDetailsScreen(navController)
        }
    }

    private fun NavGraphBuilder.albumListScreen() {
        composable(AppDestination.Albums.Main.fullRoute) {
            val viewModel = AlbumsViewModel.create(assistedFactory = albumsViewModelFactory)
            AlbumsScreen(viewModel = viewModel)
        }
    }

    private fun NavGraphBuilder.albumDetailsScreen(navController: NavController) {
        composable(
            route = AppDestination.Albums.Details.fullRoute,
            arguments = listOf(
                navArgument(AppDestination.Albums.ALBUM_ID) { type = NavType.StringType }
            )
        ) {
            val albumId = it.arguments?.getString(AppDestination.Albums.ALBUM_ID)?.toLong()

            if (albumId == null) {
                navController.popBackStack()
                return@composable
            }

            val viewModel = AlbumsDetailsViewModel.create(
                assistedFactory = albumDetailsViewModelFactory,
                albumId = albumId,
            )
            AlbumsDetailsScreen(viewModel = viewModel)
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface ComposeNavigationFactoryModule {

    @Binds
    @IntoSet
    fun bindNavigationFactory(factory: AlbumsNavigationFactory): BaseNavigationFactory
}