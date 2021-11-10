package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.notification.PostFcmDto

interface FcmRepository {
    suspend fun postFcm(postFcmDto: PostFcmDto)
}