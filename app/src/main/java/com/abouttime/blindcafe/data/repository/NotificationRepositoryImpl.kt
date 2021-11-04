package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.remote.api.NotificationApi
import com.abouttime.blindcafe.data.remote.dto.PushNotification
import com.abouttime.blindcafe.domain.repository.NotificationRepository
import okhttp3.ResponseBody
import retrofit2.Response

class NotificationRepositoryImpl(
    private val notificationApi: NotificationApi
): NotificationRepository {
    override suspend fun postNotification(notification: PushNotification): Response<ResponseBody> {
        return notificationApi.postNotification(notification = notification)
    }
}