package com.abouttime.blindcafe.data.remote.server.dto.matching.topic


import com.google.gson.annotations.SerializedName

data class GetTopicDto(
    @SerializedName("audio")
    val topicAudio: TopicAudio?,
    @SerializedName("image")
    val topicImage: TopicImage?,
    @SerializedName("text")
    val topicText: TopicText?,
    @SerializedName("type")
    val type: String?
)