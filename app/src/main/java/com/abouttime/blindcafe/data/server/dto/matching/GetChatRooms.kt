package com.abouttime.blindcafe.data.server.dto.matching


import com.google.gson.annotations.SerializedName

data class GetChatRooms(
    @SerializedName("matchings")
    val matchings: List<Matching>?
)