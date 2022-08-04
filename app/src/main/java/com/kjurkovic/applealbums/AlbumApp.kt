package com.kjurkovic.applealbums

import android.app.Application
import com.kjurkovic.common.framework.initializers.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AlbumApp : Application() {

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards AppInitializer>

    override fun onCreate() {
        super.onCreate()
        initializers.forEach { it.init(this) }
    }
}
