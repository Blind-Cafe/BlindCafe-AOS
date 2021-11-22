package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.data.firebase.Firestore
import com.abouttime.blindcafe.data.firebase.Firestorage
import org.koin.dsl.module

internal val firebaseModule = module {

    factory { Firestore() }
    factory { Firestorage() }
}