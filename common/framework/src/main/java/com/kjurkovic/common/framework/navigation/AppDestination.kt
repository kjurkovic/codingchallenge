package com.kjurkovic.common.framework.navigation


sealed class AppDestination(route: String, vararg params: String): Destination("", route, *params) {

    object Albums : AppDestination("albums") {

        const val ALBUM_ID = "album_id"
        object Main: Destination(Albums, "")
        object Details: Destination(Albums, "details/{$ALBUM_ID}") {
            operator fun invoke(albumId: Long) = getRoute(ALBUM_ID to albumId)
        }
    }
}