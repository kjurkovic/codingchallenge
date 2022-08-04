package com.kjurkovic.wrappers.logging

import android.app.Application
import com.kjurkovic.common.annotations.DebugBuild
import com.kjurkovic.common.framework.initializers.AppInitializer
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    @DebugBuild private val isDebug: Boolean,
): AppInitializer {

    override fun init(application: Application) {
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ProductionTree())
        }
    }
}
