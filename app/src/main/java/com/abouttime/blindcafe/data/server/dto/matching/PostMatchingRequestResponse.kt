package com.abouttime.blindcafe.data.server.dto.matching


import com.abouttime.blindcafe.common.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class PostMatchingRequestResponse(
    @SerializedName("matchingStatus")
    val matchingStatus: String?,
    @SerializedName("matchingId")
    val matchingId: Int?,
    @SerializedName("partnerId")
    val partnerId: Int?,
    @SerializedName("partnerNickname")
    val partnerNickname: String?
): BaseResponse()