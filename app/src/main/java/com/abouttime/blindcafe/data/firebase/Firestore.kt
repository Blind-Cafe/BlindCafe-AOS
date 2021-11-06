package com.abouttime.blindcafe.data.firebase

import com.abouttime.blindcafe.common.constants.FIREBASE_KEY
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore {
    val roomCollectionRef = Firebase.firestore.collection(FIREBASE_KEY.COLLECTION_ROOMS)
}