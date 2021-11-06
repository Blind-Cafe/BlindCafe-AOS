package com.abouttime.blindcafe.domain.use_case

import android.util.Log
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.constants.LogTag.FCM_TAG
import com.abouttime.blindcafe.data.server.dto.PushNotification
import com.abouttime.blindcafe.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response

class PostNotificationUseCase(private val repository: NotificationRepository) {
    operator fun invoke(notification: PushNotification): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.postNotification(notification = notification)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
            Log.e(FCM_TAG, e.toString())
        }
    }
}