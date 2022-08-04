package com.kjurkovic.data.database.di

import com.kjurkovic.data.database.Database
import com.kjurkovic.data.database.DatabaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModule {

    @Binds
    abstract fun provideDatabase(impl: DatabaseImpl): Database
}
