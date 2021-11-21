package com.abouttime.blindcafe.domain.use_case

import android.net.Uri
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UploadAudioUseCase(
    private val repository: FirestorageRepository
) {
    operator fun invoke(message: Message, uri: Uri): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        try {
            val response = repository.uploadAudio(
                message = message,
                uri = uri
            )
            if (response != null) {
                emit(Resource.Success(data = true))
            } else {
                emit(Resource.Error<Boolean>(message = "response is null"))
            }

        } catch (e: Exception) {
            emit(Resource.Error<Boolean>(message = e.toString()))
        }

    }
}