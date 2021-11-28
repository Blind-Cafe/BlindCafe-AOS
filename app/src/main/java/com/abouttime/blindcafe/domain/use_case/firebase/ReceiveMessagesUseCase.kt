package com.abouttime.blindcafe.domain.use_case.firebase

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestoreRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class ReceiveMessagesUseCase(
    private val repository: FirestoreRepository
) {
    operator fun invoke(roomId: String, lastTime: Timestamp): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.receiveMessages(roomId, lastTime)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            }
        }
    }
}