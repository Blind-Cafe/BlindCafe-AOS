package com.abouttime.blindcafe.data.remote.server.dto.matching.topic


import com.google.gson.annotations.SerializedName

data class TopicText(
    @SerializedName("content")
    val content: String?
)