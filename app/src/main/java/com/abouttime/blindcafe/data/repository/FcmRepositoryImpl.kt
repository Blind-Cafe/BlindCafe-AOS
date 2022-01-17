package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.remote.server.api.FcmApi
import com.abouttime.blindcafe.data.remote.server.dto.notification.PostFcmDto
import com.abouttime.blindcafe.domain.repository.FcmRepository

class FcmRepositoryImpl(
    private val fcmApi: FcmApi
): FcmRepository {
    override suspend fun postFcm(postFcmDto: PostFcmDto) {
        fcmApi.postFcm(postFcmDto)
    }
}