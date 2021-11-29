package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.domain.model.Message
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


interface FirestoreRepository {
    suspend fun sendMessage(message: Message): DocumentReference?
    suspend fun subscribeMessages(roomId: String): Flow<Resource<List<Message>>>
    suspend fun receiveMessages(roomId: String, lastTime: Timestamp): List<Message>
}