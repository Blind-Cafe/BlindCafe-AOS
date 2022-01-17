package com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.info


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