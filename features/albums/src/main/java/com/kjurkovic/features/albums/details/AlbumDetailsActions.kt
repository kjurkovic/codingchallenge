package com.kjurkovic.features.albums.details

import com.kjurkovic.common.async.Async
import com.kjurkovic.common.async.Uninitialized
import com.kjurkovic.data.repo.album.models.AlbumModel

sealed class AlbumDetailsActions {
    object BackClicked: AlbumDetailsActions()
}

data class AlbumDetailsViewState(
    val albumDetails: Async<AlbumModel> = Uninitialized,
)
