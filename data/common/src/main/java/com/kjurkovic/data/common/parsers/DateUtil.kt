package com.kjurkovic.data.common.parsers

import android.annotation.SuppressLint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
private val DATE_TIME_FORMATTER_DATE_FULL = DateTimeFormatter.ofPattern("MMMM dd, yyyy").withZone(ZoneId.systemDefault())

fun Instant.toFullDateString() = DATE_TIME_FORMATTER_DATE_FULL.format(this)