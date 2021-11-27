package com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange


import com.google.gson.annotations.SerializedName

data class PostProfileForOpenDto(
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("state")
    val state: String?
)