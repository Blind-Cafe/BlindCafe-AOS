package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.constants.FirebaseKey.SUB_COLLECTION_MESSAGES
import com.abouttime.blindcafe.common.constants.FirebaseKey.TIME_STAMP
import com.abouttime.blindcafe.common.constants.PageSize.CHAT_PAGE_SIZE
import com.abouttime.blindcafe.data.remote.firebase.Firestore
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestoreRepository
import com.google.firebase.Timestamp
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
            val time = Timestamp.now()
            val subscription =
                firestore
                    .roomCollectionRef
                    .document(roomId)
                    .collection(SUB_COLLECTION_MESSAGES)
                    .whereGreaterThanOrEqualTo(TIME_STAMP, time)
                    .orderBy(TIME_STAMP, Query.Direction.DESCENDING)
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

    override suspend fun receiveMessages(roomId: String, lastTime: Timestamp): List<Message> {
        return firestore
            .roomCollectionRef
            .document(roomId)
            .collection(SUB_COLLECTION_MESSAGES)
            .whereLessThan(TIME_STAMP, lastTime) // 인자보다 오래된 메시지 중에
            .orderBy(TIME_STAMP, Query.Direction.DESCENDING) // 최근 순으로
            .limit(CHAT_PAGE_SIZE) // 페이지 사이즈 만큼
            .get()
            .await()
            .documents
            .map { doc ->
                doc?.let {
                    doc.toObject<Message>()
                }?: kotlin.run {
                    Message()
                }
            }
    }

}