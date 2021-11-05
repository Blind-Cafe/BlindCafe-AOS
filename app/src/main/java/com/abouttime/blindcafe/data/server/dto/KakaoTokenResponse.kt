package com.abouttime.blindcafe.data.server.dto

import com.google.gson.annotations.SerializedName

data class KakaoTokenResponse(
    @SerializedName("jwt")
    val jwt: String? = null
)