package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.domain.model.Message
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow


interface FirestoreRepository {
    suspend fun sendMessage(message: Message): DocumentReference?
    suspend fun subscribeMessages(roomId: String): Flow<Resource<List<Message>>>
    suspend fun receiveMessages(roomId: String): List<Message?>

}