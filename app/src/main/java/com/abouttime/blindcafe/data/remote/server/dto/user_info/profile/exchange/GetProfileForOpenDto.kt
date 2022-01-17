package com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.exchange


import com.google.gson.annotations.SerializedName

data class GetProfileForOpenDto(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("fill")
    val fill: Boolean?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("interests")
    val interests: List<String>?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("partnerNickname")
    val partnerNickname: String?,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("userId")
    val userId: Int?
)