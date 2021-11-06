package com.abouttime.blindcafe.data.firebase

import com.abouttime.blindcafe.common.constants.FIREBASE_KEY.STORAGE_AUDIO
import com.abouttime.blindcafe.common.constants.FIREBASE_KEY.STORAGE_IMAGE
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Firestorage {
    private val storage = Firebase.storage
    val audioRef = storage.reference.child(STORAGE_AUDIO)
    val imageRef = storage.reference.child(STORAGE_IMAGE)
}