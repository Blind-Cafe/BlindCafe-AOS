package com.abouttime.blindcafe.data.local.database.dao

import androidx.room.Insert
import androidx.room.Query
import com.abouttime.blindcafe.data.local.database.entity.MessageEntity

interface MessageDao {
    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE matchingId LIKE :matchingId")
    suspend fun loadMessagesByMatchingId(matchingId: Int): List<MessageEntity>

}