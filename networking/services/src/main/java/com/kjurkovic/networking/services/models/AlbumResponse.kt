package com.kjurkovic.networking.services.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumResponse(
    @Json(name = "feed") val feed: Feed
)

@JsonClass(generateAdapter = true)
data class Feed(
    @Json(name = "copyright") val copyright: String,
    @Json(name = "results") val results: List<Album>
)
