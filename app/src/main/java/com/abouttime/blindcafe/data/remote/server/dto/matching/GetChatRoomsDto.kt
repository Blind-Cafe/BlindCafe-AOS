package com.abouttime.blindcafe.data.remote.server.dto.matching


import com.google.gson.annotations.SerializedName

data class GetChatRoomsDto(
    @SerializedName("matchings")
    val matchings: List<Matching>?
)