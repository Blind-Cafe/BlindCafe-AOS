package com.abouttime.blindcafe.data.server.dto.user_info.report


import com.google.gson.annotations.SerializedName

data class Report(
    @SerializedName("date")
    val date: String?,
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("target")
    val target: String?
)