package com.kjurkovic.networking.services

import com.kjurkovic.networking.services.models.AlbumResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumsApiService {

    @GET("/api/v2/us/music/most-played/{items}/albums.json")
    suspend fun getAlbums(@Path("items") itemsNumber: Int): Response<AlbumResponse>
}