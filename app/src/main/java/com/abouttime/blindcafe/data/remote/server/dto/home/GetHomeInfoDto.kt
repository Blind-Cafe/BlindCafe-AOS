package com.abouttime.blindcafe.data.remote.server.dto.home


import com.google.gson.annotations.SerializedName

data class GetHomeInfoDto(
    @SerializedName("code")
    val code: String?,
    @SerializedName("matchingId")
    val matchingId: Int?,
    @SerializedName("matchingStatus")
    val matchingStatus: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("partnerId")
    val partnerId: Int?,
    @SerializedName("partnerNickname")
    val partnerNickname: String?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("reason")
    val reason: String?
)