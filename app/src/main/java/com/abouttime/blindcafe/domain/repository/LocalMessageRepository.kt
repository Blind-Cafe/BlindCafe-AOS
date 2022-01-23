package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.local.database.entity.MessageEntity

interface LocalMessageRepository {

    suspend fun insertMessage(message: MessageEntity)
    suspend fun loadMessagesByMatchingId(matchingId: Int): List<MessageEntity>

}