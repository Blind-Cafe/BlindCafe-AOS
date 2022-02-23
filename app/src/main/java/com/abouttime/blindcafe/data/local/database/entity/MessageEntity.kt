package com.abouttime.blindcafe.data.local.database.entity

import androidx.room.Entity
import com.abouttime.blindcafe.domain.model.Message
import com.google.firebase.Timestamp

// TODO Message 데이터 클래스 통합
@Entity(tableName = "messages", primaryKeys = ["matchingId", "timestamp"])
data class MessageEntity(
    val matchingId: Int,
    val timestamp: Timestamp,
    val contents: String,
    val type: Int,
)