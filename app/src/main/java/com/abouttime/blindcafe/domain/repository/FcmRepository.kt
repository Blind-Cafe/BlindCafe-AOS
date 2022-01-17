package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.remote.server.dto.notification.PostFcmDto

interface FcmRepository {
    suspend fun postFcm(postFcmDto: PostFcmDto)
}