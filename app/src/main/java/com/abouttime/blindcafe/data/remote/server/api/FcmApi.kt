package com.abouttime.blindcafe.data.remote.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.FCM_URL
import com.abouttime.blindcafe.data.remote.server.dto.notification.PostFcmDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST(FCM_URL)
    suspend fun postFcm(
        @Body postFcmDto: PostFcmDto
    )
}