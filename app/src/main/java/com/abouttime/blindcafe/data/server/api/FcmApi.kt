package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.FCM_URL
import com.abouttime.blindcafe.data.server.dto.notification.PostFcmDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST(FCM_URL)
    suspend fun postFcm(
        @Body postFcmDto: PostFcmDto
    )
}