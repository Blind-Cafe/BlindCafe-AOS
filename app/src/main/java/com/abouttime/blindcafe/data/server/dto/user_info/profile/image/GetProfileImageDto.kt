package com.abouttime.blindcafe.data.server.dto.user_info.profile.image

import com.abouttime.blindcafe.common.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetProfileImageDto(
    @SerializedName("images")
    val images: List<String>?
)
