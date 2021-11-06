package com.abouttime.blindcafe.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Message(
    val contents: String = "-",
    val senderName: String = "-",
    val senderUid: String = "-",
    val roomUid: String = "-",
    val type: Int = 0,
    @ServerTimestamp
    val timestamp: Timestamp? = null
)