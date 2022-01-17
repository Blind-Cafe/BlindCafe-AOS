package com.abouttime.blindcafe.data.remote.server.dto.matching.send


import com.google.gson.annotations.SerializedName

data class PostMessageDto(
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("type")
    val type: Int?
)