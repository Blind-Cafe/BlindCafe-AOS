package com.abouttime.blindcafe.domain.use_case.remote.firebase

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestoreRepository
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


class SendMessageUseCase(private val repository: FirestoreRepository) {
    operator fun invoke(message: Message): Flow<Resource<DocumentReference>> =
        flow {
            emit(Resource.Loading<DocumentReference>())
            try {
                val docRef = repository.sendMessage(message = message)
                if (docRef == null) emit(Resource.Error<DocumentReference>(message = "null"))
                else emit(Resource.Success(docRef))

            } catch (e: Exception) {
                if (e is HttpException) {
                    val message = e.parseErrorBody()
                    emit(Resource.Error(message = message.toString()))
                }
            }
        }
}
