package com.abouttime.blindcafe.data.remote.server.dto.matching


import com.google.gson.annotations.SerializedName

data class Partner(
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("userId")
    val userId: Int?
)