package com.abouttime.blindcafe.data.remote.server.dto.matching.accept


import com.google.gson.annotations.SerializedName

data class PostAcceptMatchingDto(
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("result")
    val result: Boolean?
)