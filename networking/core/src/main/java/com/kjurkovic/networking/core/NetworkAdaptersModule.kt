package com.kjurkovic.networking.core

import com.kjurkovic.networking.core.adapters.InstantAdapter
import com.squareup.moshi.JsonAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkAdaptersModule {

    @Binds
    @IntoSet
    abstract fun bindMoshiInstantAdapter(adapter: InstantAdapter): JsonAdapter<*>

}