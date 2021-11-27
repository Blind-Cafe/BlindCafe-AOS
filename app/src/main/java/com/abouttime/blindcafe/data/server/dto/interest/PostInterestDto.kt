package com.abouttime.blindcafe.data.server.dto.interest


import com.google.gson.annotations.SerializedName

data class PostInterestDto(
    @SerializedName("interests")
    val interests: List<InterestX>?
)