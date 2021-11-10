package com.abouttime.blindcafe.data.server.dto.user_info


import com.google.gson.annotations.SerializedName

data class GetUserInfoDto(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("drinks")
    val drinks: List<Any>?,
    @SerializedName("interests")
    val interests: List<Int>?,
    @SerializedName("myGender")
    val myGender: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("profileImage")
    val profileImage: Any?,
    @SerializedName("region")
    val region: Any?
)