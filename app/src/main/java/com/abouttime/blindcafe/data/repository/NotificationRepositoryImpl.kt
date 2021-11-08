package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.server.api.NotificationApi
import com.abouttime.blindcafe.data.server.dto.PushNotificationDto
import com.abouttime.blindcafe.domain.repository.NotificationRepository
import okhttp3.ResponseBody
import retrofit2.Response

class NotificationRepositoryImpl(
    private val notificationApi: NotificationApi
): NotificationRepository {
    override suspend fun postNotification(notificationDto: PushNotificationDto): Response<ResponseBody> {
        return notificationApi.postNotification(notificationDto = notificationDto)
    }
}