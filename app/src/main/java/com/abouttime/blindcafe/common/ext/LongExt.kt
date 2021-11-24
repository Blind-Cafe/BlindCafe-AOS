package com.abouttime.blindcafe.common.ext

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun Long.millisecondToChatTime(): String {
    val simpleDataFormat = SimpleDateFormat("a hh:mm")
    return simpleDataFormat.format(Date(this))
}

@SuppressLint("SimpleDateFormat")
fun Long.secondToChatTime(): String {
    val ms = this * 1000
    val simpleDataFormat = SimpleDateFormat("a hh:mm")
    return simpleDataFormat.format(Date(ms))
}


fun Long.secondToLapse(): String {
    val currentTime = System.currentTimeMillis() / 1000
    val seconds = currentTime - this
    val minutes = (seconds / 60) % 60
    val hours = seconds / (60 * 60)
    return "%d시간 %02d분".format(hours, minutes)
}

