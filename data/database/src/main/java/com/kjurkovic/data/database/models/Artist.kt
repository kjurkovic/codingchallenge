package com.kjurkovic.data.database.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Artist(): RealmObject {
    @PrimaryKey var _id: Long = 0
    var name: String = ""

    constructor(id: Long, name: String) : this() {
        this._id = id
        this.name = name
    }
}
