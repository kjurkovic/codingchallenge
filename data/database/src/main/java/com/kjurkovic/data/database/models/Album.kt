package com.kjurkovic.data.database.models

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Album(): RealmObject {
    @PrimaryKey var _id: Long = 0
    var name: String = ""
    var releaseDate: RealmInstant = RealmInstant.MIN
    var copyright: String = ""
    var artworkThumbUrl: String? = null
    var artworkLargeUrl: String? = null
    var url: String? = null
    var artist: Artist? = null
    var genres: RealmList<Genre>? = null

    constructor(id: Long, name: String, releaseDate: RealmInstant, copyright: String, artworkThumbUrl: String?, artworkLargeUrl: String?, url: String?, artist: Artist?, genres: RealmList<Genre>): this() {
        this._id = id
        this.name = name
        this.releaseDate = releaseDate
        this.artworkThumbUrl = artworkThumbUrl
        this.artworkLargeUrl = artworkLargeUrl
        this.url = url
        this.artist = artist
        this.genres = genres
        this.copyright = copyright
    }
}
