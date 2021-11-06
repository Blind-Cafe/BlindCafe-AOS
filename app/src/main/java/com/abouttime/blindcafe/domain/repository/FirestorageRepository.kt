package com.abouttime.blindcafe.domain.repository

import android.net.Uri
import com.abouttime.blindcafe.domain.model.Message
import com.google.firebase.storage.UploadTask

interface FirestorageRepository {
    suspend fun uploadImage(message: Message, uri: Uri): UploadTask.TaskSnapshot?
    suspend fun uploadAudio(message: Message, uri: Uri): UploadTask.TaskSnapshot?
    suspend fun downloadImageUrl(message: Message): Uri?
    suspend fun downloadAudioUrl(message: Message): Uri?
}