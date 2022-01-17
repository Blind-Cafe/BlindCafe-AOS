package com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.exchange

import com.google.gson.annotations.SerializedName

data class PostProfileForOpenResponse(
    @SerializedName("result")
    val result: Boolean?,
    @SerializedName("nickname")
    val nickname: String?
)
