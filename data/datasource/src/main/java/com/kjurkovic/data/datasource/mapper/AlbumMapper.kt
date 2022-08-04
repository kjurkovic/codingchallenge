package com.kjurkovic.data.datasource.mapper

import com.kjurkovic.data.common.parsers.toFullDateString
import com.kjurkovic.data.database.models.Album
import com.kjurkovic.data.database.models.Artist
import com.kjurkovic.data.database.models.Genre
import com.kjurkovic.data.repo.album.models.AlbumModel
import com.kjurkovic.data.repo.album.models.ArtistModel
import com.kjurkovic.data.repo.album.models.GenreModel
import java.time.Instant

fun Album.toModel() = AlbumModel(
    id = _id,
    name = name,
    releaseDate = Instant.ofEpochSecond(releaseDate.epochSeconds).toFullDateString(),
    copyright = copyright,
    artworkThumb = artworkThumbUrl,
    artworkImage = artworkLargeUrl,
    artist = artist?.toModel(),
    genres = genres?.toList()?.map { it.toModel() } ?: emptyList(),
    url = url,
)

fun Artist.toModel() = ArtistModel(
    id = _id,
    name = name,
)

fun Genre.toModel() = GenreModel(
    id = _id,
    name = name,
    url = url,
)