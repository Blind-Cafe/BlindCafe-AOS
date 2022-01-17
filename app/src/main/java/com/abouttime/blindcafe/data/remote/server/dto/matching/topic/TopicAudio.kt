package com.abouttime.blindcafe.data.remote.server.dto.matching.topic


import com.google.gson.annotations.SerializedName

data class TopicAudio(
    @SerializedName("src")
    val src: String?,
    @SerializedName("title")
    val title: String?
)