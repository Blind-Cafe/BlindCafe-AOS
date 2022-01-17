package com.abouttime.blindcafe.data.remote.server.dto.interest


import com.google.gson.annotations.SerializedName

data class InterestX(
    @SerializedName("main")
    val main: Int?,
    @SerializedName("sub")
    val sub: List<String>?
)