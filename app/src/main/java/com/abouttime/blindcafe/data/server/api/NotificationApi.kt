package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.CONTENT_TYPE
import com.abouttime.blindcafe.common.constants.Retrofit.FCM_SEND
import com.abouttime.blindcafe.common.constants.Retrofit.FIREBASE_SERVER_KEY
import com.abouttime.blindcafe.data.server.dto.z.PushNotificationDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @Headers("Authorization: key=$FIREBASE_SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST(FCM_SEND)
    suspend fun postNotification(
        @Body notificationDto: PushNotificationDto
    ): Response<ResponseBody>
}