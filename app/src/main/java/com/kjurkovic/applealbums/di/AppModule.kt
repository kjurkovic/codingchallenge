package com.kjurkovic.applealbums.di

import com.kjurkovic.applealbums.BuildConfig
import com.kjurkovic.common.annotations.ApiUrl
import com.kjurkovic.common.annotations.DebugBuild
import com.kjurkovic.common.framework.navigation.AppNavigator
import com.kjurkovic.common.framework.navigation.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @DebugBuild
    fun provideIsDebugBuild(): Boolean = BuildConfig.DEBUG

    @Provides
    @ApiUrl
    fun provideApiUrl(): String = BuildConfig.API_URL

    @Provides
    @Singleton
    fun provideNavigator(): AppNavigator = AppNavigatorImpl()
}