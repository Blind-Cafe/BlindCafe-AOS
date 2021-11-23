package com.abouttime.blindcafe.data.server.dto.matching.report


import com.google.gson.annotations.SerializedName

data class PostReportDto(
    @SerializedName("matchingId")
    val matchingId: Int?,
    @SerializedName("reason")
    val reason: Int?
)