package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.POST_FCM_URL
import com.abouttime.blindcafe.data.server.dto.notification.PostFcmDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST(POST_FCM_URL)
    suspend fun postFcm(
        @Body postFcmDto: PostFcmDto
    )
}