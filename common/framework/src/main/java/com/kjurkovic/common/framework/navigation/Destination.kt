package com.kjurkovic.common.framework.navigation

import java.net.URLEncoder

open class Destination(parent: String?, path: String, vararg params: String) {
    constructor(parent: Destination, path: String, vararg params: String) : this(parent.route, path, *params)

    val route = if (parent.isNullOrEmpty()) path else "$parent/$path"

    val fullRoute: String = if (params.isEmpty()) route else {
        StringBuilder(route).append('?').apply {
            params.forEach { append("$it={$it}&") }
            deleteAt(length - 1)
        }.toString()
    }

    protected fun getRoute(vararg params: Pair<String, Any>): String {
        var destination = route
        params.forEach { destination = destination.replace("{${it.first}}", URLEncoder.encode(it.second.toString(), Charsets.UTF_8.name())) }
        return destination
    }

    operator fun invoke() = getRoute()
}
