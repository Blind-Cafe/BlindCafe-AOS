package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.PushNotificationDto
import okhttp3.ResponseBody
import retrofit2.Response

interface NotificationRepository {
    suspend fun postNotification(notificationDto: PushNotificationDto): Response<ResponseBody>
}