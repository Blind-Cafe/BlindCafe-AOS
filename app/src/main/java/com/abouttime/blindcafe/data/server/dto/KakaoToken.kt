package com.abouttime.blindcafe.data.server.dto

import com.google.gson.annotations.SerializedName

data class KakaoToken(
    @SerializedName("token")
    val token: String
)
