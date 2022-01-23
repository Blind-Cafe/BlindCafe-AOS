package com.abouttime.blindcafe.data.local.database.entity

import androidx.room.Entity
import com.google.firebase.Timestamp

// TODO Message 데이터 클래스 통합
@Entity(tableName = "messages", primaryKeys = arrayOf("matchingId", "timestamp"))
data class MessageEntity(
    val matchingId: String,
    val timestamp: Timestamp,
    val contents: String,
    val type: Int
)