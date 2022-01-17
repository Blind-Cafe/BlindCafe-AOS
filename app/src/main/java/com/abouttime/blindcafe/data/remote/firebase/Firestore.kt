package com.abouttime.blindcafe.data.remote.firebase

import com.abouttime.blindcafe.common.constants.FirebaseKey
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore {
    val roomCollectionRef = Firebase.firestore.collection(FirebaseKey.COLLECTION_ROOMS)
}