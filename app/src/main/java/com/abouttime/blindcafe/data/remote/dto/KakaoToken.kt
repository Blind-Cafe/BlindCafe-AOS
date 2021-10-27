package com.abouttime.blindcafe.data.remote.dto

import com.google.gson.annotations.SerializedName

data class KakaoToken(
    @SerializedName("token")
    val token: String
)
