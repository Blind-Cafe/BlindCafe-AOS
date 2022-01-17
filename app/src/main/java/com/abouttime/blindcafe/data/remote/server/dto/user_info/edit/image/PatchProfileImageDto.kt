package com.abouttime.blindcafe.data.remote.server.dto.user_info.edit.image

import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart

data class PatchProfileImageDto(
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("image")
    val image: Multipart
)
