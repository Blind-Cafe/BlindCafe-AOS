package com.abouttime.blindcafe.data.remote.server.dto.user_info.partner


import com.abouttime.blindcafe.domain.model.Profile
import com.google.gson.annotations.SerializedName

data class GetPartnerProfileDto(
    @SerializedName("fill")
    val fill: Boolean?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("interests")
    val interests: List<String>?,
    @SerializedName("age")
    val age: Int?,




) {

    fun toProfile(): Profile =
        Profile(
            age = age,
            sex = gender,
            interests = interests,
            nickname = nickname,
            profileImage = profileImage,
            location = region,
            userId = userId
        )

}