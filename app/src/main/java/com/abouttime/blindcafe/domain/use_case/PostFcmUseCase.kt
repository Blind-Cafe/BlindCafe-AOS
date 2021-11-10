package com.abouttime.blindcafe.domain.use_case

import android.util.Log
import com.abouttime.blindcafe.common.constants.LogTag.FCM_TAG
import com.abouttime.blindcafe.data.server.dto.notification.PostFcmDto
import com.abouttime.blindcafe.domain.repository.FcmRepository

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