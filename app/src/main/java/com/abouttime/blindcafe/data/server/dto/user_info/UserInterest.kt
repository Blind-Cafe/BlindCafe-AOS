package com.abouttime.blindcafe.data.server.dto.user_info


import com.google.gson.annotations.SerializedName

data class UserInterest(
    @SerializedName("main")
    val main: Int?,
    @SerializedName("sub")
    val sub: List<String>?
)