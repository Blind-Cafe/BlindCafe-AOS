package com.abouttime.blindcafe.data


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("text")
    val text: String?
)