package com.abouttime.blindcafe.data.server.dto

data class PushNotificationDto(
    val data: NotificationData,
    val to: String
)
