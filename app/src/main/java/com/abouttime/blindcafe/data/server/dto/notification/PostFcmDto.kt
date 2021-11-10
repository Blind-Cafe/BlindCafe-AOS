package com.abouttime.blindcafe.data.server.dto.notification


import com.google.gson.annotations.SerializedName

data class PostFcmDto(
    @SerializedName("body")
    val body: String?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("targetToken")
    val targetToken: String?,
    @SerializedName("title")
    val title: String?
)