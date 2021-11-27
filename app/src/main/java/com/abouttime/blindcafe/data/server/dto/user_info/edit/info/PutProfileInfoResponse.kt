package com.abouttime.blindcafe.data.server.dto.user_info.edit.info

import com.google.gson.annotations.SerializedName

data class PutProfileInfoResponse(
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("age")
    val age: String?,
    @SerializedName("myGender")
    val myGender: String?,
    @SerializedName("partnerGender")
    val partnerGender: String?,
    @SerializedName("region")
    val region: String?,
)
