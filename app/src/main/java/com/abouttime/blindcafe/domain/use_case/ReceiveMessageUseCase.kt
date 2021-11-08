package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class ReceiveMessageUseCase(
    private val repository: FirestoreRepository
) {
    operator fun invoke(roomId: String): Flow<Resource<List<Message>>> = flow {
        repository.receiveMessages(roomId = roomId).collect {
            emit(it)
        }
    }

}