package com.abouttime.blindcafe.data.server.dto.home


import com.google.gson.annotations.SerializedName

data class GetHomeInfoDto(
    @SerializedName("code")
    val code: String?,
    @SerializedName("matchingId")
    val matchingId: Any?,
    @SerializedName("matchingStatus")
    val matchingStatus: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("partnerId")
    val partnerId: Any?,
    @SerializedName("partnerNickname")
    val partnerNickname: Any?,
    @SerializedName("startTime")
    val startTime: Any?
)