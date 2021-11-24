package com.abouttime.blindcafe.common.ext

import android.annotation.SuppressLint
import android.icu.text.DateFormat
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

