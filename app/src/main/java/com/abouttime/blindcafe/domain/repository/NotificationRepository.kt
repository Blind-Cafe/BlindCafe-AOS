package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response

interface NotificationRepository {
    suspend fun postNotification(notification: PushNotification): Response<ResponseBody>
}