package com.abouttime.blindcafe.common.ext

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import com.abouttime.blindcafe.common.constants.Time
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
internal fun Long.millisecondToChatTime(): String {
    val simpleDataFormat = SimpleDateFormat("a hh:mm")
    return simpleDataFormat.format(Date(this))
}

@SuppressLint("SimpleDateFormat")
internal fun Long.secondToChatTime(): String {
    val ms = this * 1000
    val simpleDataFormat = SimpleDateFormat("a hh:mm")
    return simpleDataFormat.format(Date(ms))
}


internal fun Long.secondToLapseForChat(): String {
    val currentTime = System.currentTimeMillis() / 1000
    val seconds = currentTime - this
    val minutes = (seconds / 60) % 60
    val hours = seconds / (60 * 60)
    return "%02d시간 %02d분".format(hours, minutes)
}

internal fun Long.secondToLapseForHome(): String {
    val currentTime = System.currentTimeMillis() / 1000
    val seconds = currentTime - this
    val minutes = (seconds / 60) % 60
    val hours = seconds / (60 * 60)
    return "%02d:%02d".format(hours, minutes)
}


internal fun Long.isOver24Hours(): Boolean {
    val currentTime = System.currentTimeMillis() / 1000
    val seconds = currentTime - this
    return seconds > (Time.HOUR_24 * 60 * 60).toLong()
}

internal fun Long.isOver48Hours(): Boolean {
    val currentTime = System.currentTimeMillis() / 1000
    val seconds = currentTime - this
    return seconds > (Time.HOUR_48 * 60 * 60).toLong()
}

internal fun Long.isOver72Hours(): Boolean {
    val currentTime = System.currentTimeMillis() / 1000
    val seconds = currentTime - this
    return seconds > (Time.HOUR_72 * 60 * 60).toLong()
}

internal fun Long.to3RangeDays(): Int = if (!this.isOver24Hours()) 1 else if (!this.isOver48Hours()) 2 else 3
