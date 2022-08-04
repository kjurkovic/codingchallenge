package com.kjurkovic.common.framework.initializers

import android.app.Application

interface AppInitializer {

    fun init(application: Application)
}