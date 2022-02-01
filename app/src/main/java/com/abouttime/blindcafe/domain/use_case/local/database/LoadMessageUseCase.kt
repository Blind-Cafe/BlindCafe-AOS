package com.abouttime.blindcafe.domain.use_case.local.database

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.local.database.entity.MessageEntity
import com.abouttime.blindcafe.domain.repository.LocalMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadMessageUseCase(
    private val repository: LocalMessageRepository
) {
    operator fun invoke(matchingId: Int): Flow<Resource<List<MessageEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.loadMessagesByMatchingId(matchingId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.toString()))
        }

    }
}