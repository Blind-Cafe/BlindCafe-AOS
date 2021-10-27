package com.abouttime.blindcafe.data.remote.dto

import com.google.gson.annotations.SerializedName

data class KakaoTokenResponse(
    @SerializedName("jwt")
    val jwt: String? = null
)