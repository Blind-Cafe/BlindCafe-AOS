package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.local.database.ChatDatabase
import com.abouttime.blindcafe.data.local.database.entity.MessageEntity
import com.abouttime.blindcafe.domain.repository.LocalMessageRepository

class LocalMessageRepositoryImpl(
    private val database: ChatDatabase
): LocalMessageRepository {
    override suspend fun insertMessage(message: MessageEntity) {
        database.messageDao().insertMessage(message)
    }

    override suspend fun loadMessagesByMatchingId(matchingId: Int): List<MessageEntity> {
        return database.messageDao().loadMessagesByMatchingId(matchingId)
    }
}