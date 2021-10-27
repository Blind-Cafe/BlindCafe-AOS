package com.abouttime.blindcafe.common.base.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("code")
    val code: String?,
    val message: String?
)
