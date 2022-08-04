package com.kjurkovic.data.repo.album

import com.kjurkovic.data.repo.album.models.AlbumModel
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    suspend fun refresh()
    suspend fun getAlbums(): Flow<List<AlbumModel>>
    suspend fun getAlbum(id: Long): Flow<AlbumModel>
}