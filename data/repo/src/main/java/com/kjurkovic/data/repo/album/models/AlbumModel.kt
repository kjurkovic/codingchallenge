package com.kjurkovic.data.repo.album.models

data class AlbumModel(
    val id: Long,
    val name: String,
    val releaseDate: String,
    val copyright: String,
    val artworkThumb: String?,
    val artworkImage: String?,
    val artist: ArtistModel?,
    val genres: List<GenreModel>,
    val url: String?
)