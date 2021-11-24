package com.abouttime.blindcafe.data.server.dto.user_info.profile


import com.google.gson.annotations.SerializedName

data class GetProfileInfoDto(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("myGender")
    val myGender: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("partnerGender")
    val partnerGender: String?,
    @SerializedName("region")
    val region: String?
)