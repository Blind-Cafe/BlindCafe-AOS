package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestoreRepository
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SendMessageUseCase(private val repository: FirestoreRepository) {
    operator fun invoke(message: Message): Flow<Resource<DocumentReference>> =
        flow {
            emit(Resource.Loading<DocumentReference>())
            try {
                val docRef = repository.sendMessage(message = message)
                if (docRef == null) emit(Resource.Error<DocumentReference>(message = "null"))
                else emit(Resource.Success(docRef))

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error<DocumentReference>(message = e.toString()))
            }
        }
}
