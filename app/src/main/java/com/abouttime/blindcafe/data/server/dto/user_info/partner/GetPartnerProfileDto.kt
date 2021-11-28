package com.abouttime.blindcafe.data.server.dto.user_info.partner


import com.abouttime.blindcafe.domain.model.Profile
import com.google.gson.annotations.SerializedName

data class GetPartnerProfileDto(
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
) {

    fun toProfile(): Profile =
        Profile(
            age = age,
            sex = gender,
            interests = interests,
            nickname = nickname,
            partnerNickname = partnerNickname,
            profileImage = profileImage,
            location = region,
            userId = userId
        )

}