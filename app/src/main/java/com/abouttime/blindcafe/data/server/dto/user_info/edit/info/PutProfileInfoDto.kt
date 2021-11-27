package com.abouttime.blindcafe.data.server.dto.user_info.edit.info

import com.google.gson.annotations.SerializedName

data class PutProfileInfoDto(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("partnerGender")
    val partnerGender: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("region")
    val region: String,
)
