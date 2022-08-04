package com.kjurkovic.networking.core.adapters

import android.annotation.SuppressLint
import com.squareup.moshi.*
import timber.log.Timber
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@SuppressLint("NewApi")
class InstantAdapter @Inject constructor() : JsonAdapter<Instant?>() {

    @FromJson
    override fun fromJson(reader: JsonReader): Instant? {
        return reader.readJsonValue()?.toString()?.let {
            try {
                return@let LocalDate.parse(it).atStartOfDay(ZoneId.systemDefault()).toInstant()
            } catch (ex: DateTimeParseException) {
                Timber.v(ex, "Unable to parse Instant with DATE_FORMATTER")
                null
            }
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Instant?) {
        if (value != null) writer.value(value.toString())
        else writer.nullValue()
    }
}
