package com.kjurkovic.common.wrappers.dispatchers

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DispatchersModule {

    @Binds
    fun provideDispatcherProvider(impl: DefaultDispatcherProvider): DispatcherProvider
}
