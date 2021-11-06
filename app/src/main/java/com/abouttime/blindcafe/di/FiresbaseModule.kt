package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.data.firebase.Firestore
import com.abouttime.blindcafe.data.firebase.Firestorage
import org.koin.dsl.module

internal val firebaseModule = module {

    factory { Firestore() }
    factory { Firestorage() }
    //factory { Firebase.storage.reference.child(FIREBASE_KEY.STORAGE_IMAGE) }
    //factory { Firebase.storage.reference.child(FIREBASE_KEY.STORAGE_AUDIO) }
}