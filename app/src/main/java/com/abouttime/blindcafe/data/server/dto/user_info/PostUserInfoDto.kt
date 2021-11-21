package com.abouttime.blindcafe.data.server.dto.user_info


import com.google.gson.annotations.SerializedName

data class PostUserInfoDto(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("interests")
    val userInterests: List<UserInterest>?,
    @SerializedName("myGender")
    val myGender: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("partnerGender")
    val partnerGender: String?
)