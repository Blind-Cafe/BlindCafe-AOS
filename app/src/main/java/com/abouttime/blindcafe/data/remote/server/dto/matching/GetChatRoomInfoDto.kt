package com.abouttime.blindcafe.data.remote.server.dto.matching


import com.abouttime.blindcafe.domain.model.ChatRoom
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
    val interest: String?,
    @SerializedName("continuous")
    val continuous: Boolean
) {

    fun toChatRoom(): ChatRoom =
        ChatRoom(
            matchingId = matchingId,
            nickname = nickname,
            profileImage = profileImage,
            drink = drink,
            startTime = startTime,
            interest = interest,
            continuous = continuous
        )

}