package com.abouttime.blindcafe.data.server.dto.matching.topic


import com.google.gson.annotations.SerializedName

data class GetTopicDto(
    @SerializedName("audio")
    val audio: Audio?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("text")
    val text: Text?,
    @SerializedName("type")
    val type: String?
)