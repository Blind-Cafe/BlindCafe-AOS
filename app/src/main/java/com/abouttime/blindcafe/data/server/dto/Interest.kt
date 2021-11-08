package com.abouttime.blindcafe.data.server.dto


import com.google.gson.annotations.SerializedName

data class Interest(
    @SerializedName("main")
    val main: Int?,
    @SerializedName("sub")
    val sub: List<String>?
)