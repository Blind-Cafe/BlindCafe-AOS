package com.abouttime.blindcafe.data.remote.server.dto.user_info


import com.abouttime.blindcafe.common.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetUserInfoDto(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("drinks")
    val drinks: List<Int>?,
    @SerializedName("interests")
    val interests: List<Int>?,
    @SerializedName("myGender")
    val myGender: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("partnerGender")
    val partnerGender: String?
): BaseResponse()