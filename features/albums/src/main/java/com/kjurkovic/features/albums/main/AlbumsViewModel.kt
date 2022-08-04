package com.kjurkovic.features.albums.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kjurkovic.common.async.Loading
import com.kjurkovic.common.async.Uninitialized
import com.kjurkovic.common.framework.navigation.AppDestination
import com.kjurkovic.common.framework.navigation.AppNavigator
import com.kjurkovic.common.mvvm.BaseViewModel
import com.kjurkovic.common.wrappers.dispatchers.DispatcherProvider
import com.kjurkovic.data.repo.album.AlbumRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AlbumsViewModel @AssistedInject constructor(
    private val albumRepository: AlbumRepository,
    private val navigator: AppNavigator,
    dispatcherProvider: DispatcherProvider,
): BaseViewModel<AlbumsViewState, AlbumActions, Nothing>(
    dispatcherProvider = dispatcherProvider,
    viewState = AlbumsViewState()
){

    @AssistedFactory
    interface Factory {
        fun create(): AlbumsViewModel
    }

    companion object {
        @Composable
        fun create(assistedFactory: Factory): AlbumsViewModel =
            viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create() as T
                }
            })
    }

    init {
        suspend {
            refreshAlbumsData()
        }.execute {}

        launchIo {
            albumRepository.getAlbums().subscribe { albumsAsync ->
                setState { copy(albumList = albumsAsync) }
            }
        }
    }

    override suspend fun reduce(action: AlbumActions) {
        when (action) {
            is AlbumActions.AlbumClicked -> navigator.navigateTo(AppDestination.Albums.Details(action.id))
            AlbumActions.RetryClicked -> {
                setState { copy(albumList = Uninitialized) }
                refreshAlbumsData()
            }
        }
    }

    private fun refreshAlbumsData() {
        launchIo {
            runCatching {
                albumRepository.refresh()
            }.onFailure {
                setState { copy(isNetworkFetchFailed = true) }
            }
        }
    }
}