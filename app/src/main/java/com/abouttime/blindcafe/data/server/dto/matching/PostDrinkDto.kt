package com.abouttime.blindcafe.data.server.dto.matching

import com.google.gson.annotations.SerializedName

data class PostDrinkDto (
    @SerializedName("drink")
    val drink: Int?
)