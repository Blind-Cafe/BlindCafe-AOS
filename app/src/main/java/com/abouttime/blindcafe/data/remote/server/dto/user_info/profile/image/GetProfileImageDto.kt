package com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.image

import com.google.gson.annotations.SerializedName

data class GetProfileImageDto(
    @SerializedName("images")
    val images: List<String>?
)
