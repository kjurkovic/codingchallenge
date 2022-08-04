package com.kjurkovic.data.database.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Genre(): RealmObject {
    @PrimaryKey var _id: Long = 0
    var name: String = ""
    var url: String? = null

    constructor(id: Long, name: String, url: String?): this() {
        this._id = id
        this.name = name
        this.url = url
    }
}
