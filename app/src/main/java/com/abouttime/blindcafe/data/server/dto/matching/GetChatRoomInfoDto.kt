package com.abouttime.blindcafe.data.server.dto.matching


import com.google.gson.annotations.SerializedName

data class GetChatRoomInfoDto(
    @SerializedName("drink")
    val drink: String?,
    @SerializedName("matchingId")
    val matchingId: Int?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("profileImage")
    val profileImage: Any?,
    @SerializedName("startTime")
    val startTime: String?
)