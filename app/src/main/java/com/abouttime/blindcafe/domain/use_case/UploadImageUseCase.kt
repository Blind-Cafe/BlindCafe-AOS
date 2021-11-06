package com.abouttime.blindcafe.domain.use_case

import android.net.Uri
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UploadImageUseCase(
    private val repository: FirestorageRepository
) {
    operator fun invoke(message: Message, uri: Uri): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.uploadImage(
                message = message,
                uri = uri
            )
            if (response != null) {
                emit(Resource.Success(data = true))
            } else {
                emit(Resource.Error(message = "response is null"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(message = e.toString()))
        }
    }
}