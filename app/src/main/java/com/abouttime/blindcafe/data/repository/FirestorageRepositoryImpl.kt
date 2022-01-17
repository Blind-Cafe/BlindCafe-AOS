package com.abouttime.blindcafe.data.repository

import android.net.Uri
import com.abouttime.blindcafe.data.remote.firebase.Firestorage
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestorageRepository
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await

class FirestorageRepositoryImpl(
    private val firebaseStorage: Firestorage
): FirestorageRepository {
    override suspend fun uploadImage(message: Message, uri: Uri): UploadTask.TaskSnapshot? {
        val id = message.contents
        return firebaseStorage
            .imageRef
            .child(id)
            .putFile(uri)
            .await()
    }

    override suspend fun uploadAudio(message: Message, uri: Uri): UploadTask.TaskSnapshot? {
        val id = message.contents
        return firebaseStorage
            .audioRef
            .child(id)
            .putFile(uri)
            .await()
    }

    override suspend fun downloadImageUrl(message: Message): Uri? {
        val id = message.contents
        return firebaseStorage
            .imageRef
            .child(id)
            .downloadUrl
            .await()
    }

    override suspend fun downloadAudioUrl(message: Message): Uri? {
        val id = message.contents
        return firebaseStorage
            .audioRef
            .child(id)
            .downloadUrl
            .await()
    }

}