package com.abouttime.blindcafe.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abouttime.blindcafe.data.local.database.entity.MessageEntity

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE matchingId LIKE :matchingId")
    suspend fun loadMessagesByMatchingId(matchingId: Int): List<MessageEntity> // TODO Paging 적용 예정

}