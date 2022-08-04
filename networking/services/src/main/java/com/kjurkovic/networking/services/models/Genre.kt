package com.kjurkovic.networking.services.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "genreId") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String?,
)
