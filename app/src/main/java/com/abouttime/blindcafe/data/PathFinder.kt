package com.abouttime.blindcafe.data


import com.google.gson.annotations.SerializedName

data class PathFinder(
    @SerializedName("items")
    val items: List<Item>?
)