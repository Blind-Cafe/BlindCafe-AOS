package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.data.remote.firebase.Firestore
import com.abouttime.blindcafe.data.remote.firebase.Firestorage
import org.koin.dsl.module

internal val firebaseModule = module {

    factory { Firestore() }
    factory { Firestorage() }
}