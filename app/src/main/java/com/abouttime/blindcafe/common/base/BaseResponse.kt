package com.abouttime.blindcafe.common.base

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("code")
    val code: String?,
    val message: String?
)
