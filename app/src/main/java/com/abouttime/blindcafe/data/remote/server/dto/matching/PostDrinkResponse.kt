package com.abouttime.blindcafe.data.remote.server.dto.matching


import com.google.gson.annotations.SerializedName

data class PostDrinkResponse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("startTime")
    val startTime: String?
)