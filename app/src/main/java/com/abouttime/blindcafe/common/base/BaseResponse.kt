package com.abouttime.blindcafe.common.base

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("code")
    val code: String? = "0",
    @SerializedName("message")
    val message: String? = ""
)
