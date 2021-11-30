package com.abouttime.blindcafe.data.server.dto.matching


import com.google.gson.annotations.SerializedName

data class Matching(
    @SerializedName("expiryTime")
    val expiryDay: Int?,
    @SerializedName("latestMessage")
    val latestMessage: String?,
    @SerializedName("matchingId")
    val matchingId: Int?,
    @SerializedName("partner")
    val partner: Partner?,
    @SerializedName("received")
    val received: Boolean?
)