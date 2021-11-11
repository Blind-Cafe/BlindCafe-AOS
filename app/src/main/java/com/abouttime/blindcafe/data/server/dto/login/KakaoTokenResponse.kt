package com.abouttime.blindcafe.data.server.dto.login

import com.abouttime.blindcafe.common.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class KakaoTokenResponse(
    @SerializedName("jwt")
    val jwt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("nickname")
    val nickname: String? = null
): BaseResponse()