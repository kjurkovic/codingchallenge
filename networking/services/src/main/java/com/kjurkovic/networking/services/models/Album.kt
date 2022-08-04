package com.kjurkovic.networking.services.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant


@JsonClass(generateAdapter = true)
data class Album(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "releaseDate") val releaseDate: Instant,
    @Json(name = "artistName") val artistName: String?,
    @Json(name = "artistId") val artistId: Long?,
    @Json(name = "artworkUrl100") val artworkUrl: String?,
    @Json(name = "url") val albumUrl: String?,
    @Json(name = "genres") val genres: List<Genre>?
)
