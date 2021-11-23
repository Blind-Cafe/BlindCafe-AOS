package com.abouttime.blindcafe.data.server.dto.matching.topic


import com.google.gson.annotations.SerializedName

data class Text(
    @SerializedName("content")
    val content: String?
)