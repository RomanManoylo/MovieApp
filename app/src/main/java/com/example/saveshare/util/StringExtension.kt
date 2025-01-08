package com.example.saveshare.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.convertDateFormat(fromFormat: String, toFormat: String): String? {
    return try {
        val fromFormatter = SimpleDateFormat(fromFormat, Locale.getDefault())
        val toFormatter = SimpleDateFormat(toFormat, Locale.getDefault())

        val date = fromFormatter.parse(this)

        toFormatter.format(date as Date)
    } catch (e: Exception) {
        null
    }
}