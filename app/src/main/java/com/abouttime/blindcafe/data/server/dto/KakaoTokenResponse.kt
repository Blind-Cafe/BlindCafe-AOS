package com.abouttime.blindcafe.data.server.dto

import com.abouttime.blindcafe.common.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class KakaoTokenResponse(
    @SerializedName("jwt")
    val jwt: String? = null
): BaseResponse()