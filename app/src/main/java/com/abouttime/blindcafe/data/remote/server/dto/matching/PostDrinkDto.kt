package com.abouttime.blindcafe.data.remote.server.dto.matching

import com.google.gson.annotations.SerializedName

data class PostDrinkDto (
    @SerializedName("drink")
    val drink: Int?
)