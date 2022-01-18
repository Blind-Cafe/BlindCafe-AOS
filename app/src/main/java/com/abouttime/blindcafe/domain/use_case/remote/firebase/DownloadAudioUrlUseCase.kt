package com.abouttime.blindcafe.domain.use_case.remote.firebase

import android.net.Uri
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class DownloadAudioUrlUseCase(
    private val firestorageRepository: FirestorageRepository
) {
    operator fun invoke(message: Message): Flow<Resource<Uri>> = flow {

        emit(Resource.Loading<Uri>())

        try {
            val response = firestorageRepository
                .downloadAudioUrl(
                    message = message
                )
            if (response != null) {
                emit(Resource.Success(data = response))
            } else {
                emit(Resource.Error<Uri>(message = "response is null!"))
            }

        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            }
        }

    }
}