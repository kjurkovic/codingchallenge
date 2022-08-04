package com.kjurkovic.wrappers.logging

import timber.log.Timber

class ProductionTree: Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // TODO: add crashlytics for prod builds
    }
}