package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.constants.FirebaseKey
import com.abouttime.blindcafe.common.constants.FirebaseKey.SUB_COLLECTION_MESSAGES
import com.abouttime.blindcafe.data.firebase.Firestore
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestoreRepository
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreRepositoryImpl(
    private val firestore: Firestore,
) : FirestoreRepository {
    override suspend fun sendMessage(message: Message): DocumentReference? {
        return firestore
            .roomCollectionRef
            .document(message.roomUid)
            .collection(SUB_COLLECTION_MESSAGES)
            .add(message)
            .await()
    }


    @ExperimentalCoroutinesApi
    override suspend fun subscribeMessages(roomId: String): Flow<Resource<List<Message>>> =
        callbackFlow {
            val subscription =
                firestore
                    .roomCollectionRef
                    .document(roomId)
                    .collection(SUB_COLLECTION_MESSAGES)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (snapshot != null) {
                            val messages = mutableListOf<Message>()
                             snapshot.documentChanges.forEach { dc ->
                                 if (dc.type == DocumentChange.Type.ADDED) {
                                     messages.add(dc.document.toObject<Message>())
                                 }
                             }
                            trySend(Resource.Success(messages))

                        } else {
                            trySend(Error(error?.message ?: error.toString()))
                        }

                    }
            awaitClose {
                subscription.remove()
            }


        } as Flow<Resource<List<Message>>>

    override suspend fun receiveMessages(roomId: String, startAt: Int, endAt: Int): List<Message?> {
        return firestore
            .roomCollectionRef
            .document(roomId)
            .collection(SUB_COLLECTION_MESSAGES)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .await()
            .documents
            .map { doc ->
                doc.toObject<Message>()
            }
    }
}