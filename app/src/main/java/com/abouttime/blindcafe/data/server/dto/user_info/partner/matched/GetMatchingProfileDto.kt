package com.abouttime.blindcafe.data.server.dto.user_info.partner.matched


import com.google.gson.annotations.SerializedName

data class GetMatchingProfileDto(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("interests")
    val interests: List<String>?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("region")
    val region: String?
)