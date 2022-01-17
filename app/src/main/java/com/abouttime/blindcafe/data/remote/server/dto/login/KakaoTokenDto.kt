package com.abouttime.blindcafe.data.remote.server.dto.login

import com.google.gson.annotations.SerializedName

data class KakaoTokenDto(
    @SerializedName("token")
    val token: String,
    @SerializedName("deviceId")
    val deviceId: String
)
