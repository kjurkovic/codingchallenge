package com.kjurkovic.data.datasource.di

import com.kjurkovic.data.datasource.AlbumDatasource
import com.kjurkovic.data.repo.album.AlbumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AlbumRepositoryModule {

    @Binds
    fun bindAlbumRepository(albumRepository: AlbumDatasource): AlbumRepository
}
