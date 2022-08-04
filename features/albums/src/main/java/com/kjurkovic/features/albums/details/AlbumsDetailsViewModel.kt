package com.kjurkovic.features.albums.details

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kjurkovic.common.framework.navigation.AppDestination
import com.kjurkovic.common.framework.navigation.AppNavigator
import com.kjurkovic.common.mvvm.BaseViewModel
import com.kjurkovic.common.wrappers.dispatchers.DispatcherProvider
import com.kjurkovic.data.repo.album.AlbumRepository
import com.kjurkovic.features.albums.main.AlbumActions
import com.kjurkovic.features.albums.main.AlbumsViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AlbumsDetailsViewModel @AssistedInject constructor(
    @Assisted("albumId") private val albumId: Long,
    private val albumRepository: AlbumRepository,
    private val navigator: AppNavigator,
    dispatcherProvider: DispatcherProvider,
): BaseViewModel<AlbumDetailsViewState, AlbumDetailsActions, Nothing>(
    dispatcherProvider = dispatcherProvider,
    viewState = AlbumDetailsViewState()
){

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("albumId") albumId: Long,
        ): AlbumsDetailsViewModel
    }

    companion object {
        @Composable
        fun create(
            assistedFactory: Factory,
            albumId: Long,
        ): AlbumsDetailsViewModel =
            viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(
                        albumId = albumId,
                    ) as T
                }
            })
    }

    init {
        launchIo {
            albumRepository.getAlbum(albumId).subscribe { albumAsync ->
                setState { copy(albumDetails = albumAsync) }
            }
        }
    }

    override suspend fun reduce(action: AlbumDetailsActions) {
        when (action) {
            AlbumDetailsActions.BackClicked -> navigator.navigateBack()
        }
    }
}
