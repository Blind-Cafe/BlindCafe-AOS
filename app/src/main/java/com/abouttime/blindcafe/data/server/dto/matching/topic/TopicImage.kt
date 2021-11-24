package com.abouttime.blindcafe.data.server.dto.matching.topic


import com.google.gson.annotations.SerializedName

data class TopicImage(
    @SerializedName("src")
    val src: String?,
    @SerializedName("title")
    val title: String?
)