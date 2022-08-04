package com.kjurkovic.data.datasource

import com.kjurkovic.common.wrappers.dispatchers.DispatcherProvider
import com.kjurkovic.data.database.Database
import com.kjurkovic.data.database.models.Album
import com.kjurkovic.data.database.models.Artist
import com.kjurkovic.data.database.models.Genre
import com.kjurkovic.data.datasource.mapper.toModel
import com.kjurkovic.data.repo.album.AlbumRepository
import com.kjurkovic.data.repo.album.models.AlbumModel
import com.kjurkovic.networking.core.handlers.ApiHandler
import com.kjurkovic.networking.services.AlbumsApiService
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception

class AlbumDatasource @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val albumsApiService: AlbumsApiService,
    private val database: Database,
    private val apiHandler: ApiHandler,
) : AlbumRepository {

    companion object {
        private const val ALBUM_NUMBER = 100
        private const val THUMB_SIZE = "300x300bb.jpg"
        private const val LARGE_SIZE = "1000x1000bb.jpg"
    }

    override suspend fun refresh(): Unit = withContext(dispatcherProvider.io()) {
        val feed = apiHandler.handleCall { albumsApiService.getAlbums(ALBUM_NUMBER) }.feed
        val response = feed.results

        val genres = response.mapNotNull { it.genres }.flatten().toSet().map { Genre(it.id, it.name, it.url) }
        val artists = response.mapNotNull { album ->
            album.artistId?.let { artistId ->
                album.artistName?.let { artistName ->
                    return@mapNotNull Artist(artistId, artistName)
                }
            }
            null
        }.toSet()

        val albums = response.map {
            val artworkPath = getArtworkPath(it.artworkUrl)
            val artworkLarge = artworkPath?.plus(LARGE_SIZE)
            val artworkThumb = artworkPath?.plus(THUMB_SIZE)
            val artist = artists.firstOrNull { artist -> artist._id == it.artistId }
            val albumGenres =
                genres.filter { genre -> it.genres?.map { it.id }?.contains(genre._id) ?: false }

            return@map Album(
                it.id,
                it.name,
                RealmInstant.from(it.releaseDate.epochSecond, 0),
                feed.copyright,
                artworkThumb,
                artworkLarge,
                it.albumUrl,
                artist,
                albumGenres.toRealmList()
            )
        }

        database.realm.write {
            genres.forEach { this.copyToRealm(it, UpdatePolicy.ALL) }
            artists.forEach { this.copyToRealm(it, UpdatePolicy.ALL) }
            albums.forEach { this.copyToRealm(it, UpdatePolicy.ALL) }
        }
    }

    override suspend fun getAlbums(): Flow<List<AlbumModel>> =
        withContext(dispatcherProvider.io()) {
            database.realm.query(Album::class).asFlow()
                .map { it.list.toList().map { it.toModel() } }
        }

    override suspend fun getAlbum(id: Long): Flow<AlbumModel> =
        withContext(dispatcherProvider.io()) {
            database.realm.query(Album::class, "_id == $0", id).first().asFlow()
                .mapNotNull { it.obj?.toModel() }
        }

    /**
     * This is a hacky solution but json response delivers only one small thumb size
     * If we use that one images are blurry which affects UX
     */
    private fun getArtworkPath(url: String?): String? {
        if (url == null) return null
        return try {
            val idx = url.lastIndexOf("/")
            url.substring(0, idx + 1)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}