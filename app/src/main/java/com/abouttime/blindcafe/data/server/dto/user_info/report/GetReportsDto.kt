package com.abouttime.blindcafe.data.server.dto.user_info.report


import com.google.gson.annotations.SerializedName

data class GetReportsDto(
    @SerializedName("reports")
    val reports: List<Report>?
)