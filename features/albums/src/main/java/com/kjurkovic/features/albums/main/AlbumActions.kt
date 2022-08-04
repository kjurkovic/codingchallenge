package com.kjurkovic.features.albums.main

import com.kjurkovic.common.async.Async
import com.kjurkovic.common.async.Uninitialized
import com.kjurkovic.data.repo.album.models.AlbumModel

sealed class AlbumActions {
    data class AlbumClicked(val id: Long): AlbumActions()
    object RetryClicked: AlbumActions()
}

data class AlbumsViewState(
    val albumList: Async<List<AlbumModel>> = Uninitialized,
    val isNetworkFetchFailed: Boolean = false,
)
