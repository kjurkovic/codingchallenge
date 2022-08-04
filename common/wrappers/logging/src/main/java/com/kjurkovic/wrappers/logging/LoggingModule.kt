package com.kjurkovic.wrappers.logging

import com.kjurkovic.common.framework.initializers.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingModule {
    @Binds
    @IntoSet
    abstract fun bindTimberInitializer(bind: TimberInitializer): AppInitializer
}
