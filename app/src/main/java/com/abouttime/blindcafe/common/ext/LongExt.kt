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

