package com.kjurkovic.data.database

import com.kjurkovic.data.database.models.Album
import com.kjurkovic.data.database.models.Artist
import com.kjurkovic.data.database.models.Genre
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Inject

interface Database {
    val realm: Realm
}

class DatabaseImpl @Inject constructor() : Database {

    override val realm: Realm

    init {
        val config = RealmConfiguration.Builder(schema = setOf(
            Album::class,
            Artist::class,
            Genre::class,
        )).build()
        realm = Realm.open(config)
    }
}
