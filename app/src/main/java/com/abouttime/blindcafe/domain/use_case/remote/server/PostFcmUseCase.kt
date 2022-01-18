package com.abouttime.blindcafe.domain.use_case.remote.server

import android.util.Log
import com.abouttime.blindcafe.common.constants.LogTag.FCM_TAG
import com.abouttime.blindcafe.data.remote.server.dto.notification.PostFcmDto
import com.abouttime.blindcafe.domain.repository.FcmRepository

/**
 * FCM 전송 (임시)
 */
class PostFcmUseCase(
    private val repository: FcmRepository
) {
    suspend operator fun invoke(postFcmDto: PostFcmDto) {
        try {
            repository.postFcm(postFcmDto)
        } catch (e: Exception) {
            Log.e(FCM_TAG, e.toString())
        }

    }
}