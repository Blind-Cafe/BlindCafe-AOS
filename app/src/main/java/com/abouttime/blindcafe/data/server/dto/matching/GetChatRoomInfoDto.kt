package com.abouttime.blindcafe.data.server.dto.matching


import com.google.gson.annotations.SerializedName

data class GetChatRoomInfoDto(
    @SerializedName("matchingId")
    val matchingId: Int?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("drink")
    val drink: String?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("interest")
    val interest: String?
)