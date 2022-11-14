package com.vmake.app.base.helper

import java.text.SimpleDateFormat
import java.util.*


fun getTimeNow(): Date {
    return Date()
}

fun String?.formatTime(): Date? {
    return try {
        if (this != null) {
            getTimeFormat().parse(this)
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}

fun Date?.formatHHmm(): String? = if (this != null) getTimeFormatHHmm().format(this) else null

private fun getTimeFormatHHmm() = SimpleDateFormat("HH:mm", Locale.getDefault())
private fun getTimeFormat() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())