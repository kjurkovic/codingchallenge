package com.kjurkovic.networking.services.di

import com.kjurkovic.networking.services.AlbumsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AlbumsApiModule {

    @Provides
    @Singleton
    fun provideAlbumsApiService(retrofit: Retrofit) = retrofit.create(AlbumsApiService::class.java)
}
