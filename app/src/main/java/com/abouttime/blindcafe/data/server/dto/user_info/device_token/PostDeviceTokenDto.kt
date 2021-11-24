package com.abouttime.blindcafe.data.server.dto.user_info.device_token

import com.google.gson.annotations.SerializedName

data class PostDeviceTokenDto(
    @SerializedName("token")
    val token: String
)
