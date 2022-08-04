package com.kjurkovic.networking.core

import com.kjurkovic.common.annotations.ApiUrl
import com.kjurkovic.common.annotations.DebugBuild
import com.kjurkovic.networking.core.handlers.ApiHandler
import com.kjurkovic.networking.core.handlers.ApiHandlerImpl
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun bindsApiHandler(impl: ApiHandlerImpl): ApiHandler = impl

    @Singleton
    @Provides
    fun provideLoggingInterceptor(@DebugBuild isDebug: Boolean): HttpLoggingInterceptor? {
        return if (isDebug) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else null
    }

    @Singleton
    @Provides
    fun provideMoshi(
        adapters: Set<@JvmSuppressWildcards JsonAdapter<*>>
    ): Moshi {
        return Moshi.Builder()
            .apply { adapters.forEach { adapter -> add(adapter) } }
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshiConverter(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor?,
    ): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (httpLoggingInterceptor != null) {
                addInterceptor(httpLoggingInterceptor)
            }
        }
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okClient: Lazy<OkHttpClient>,
        moshiConverterFactory: MoshiConverterFactory,
        @ApiUrl apiUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .callFactory { okClient.get().newCall(it) }
            .addConverterFactory(moshiConverterFactory)
            .build()
    }
}
