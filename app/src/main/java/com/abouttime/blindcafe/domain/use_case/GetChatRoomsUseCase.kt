package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.matching.GetChatRooms
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChatRoomsUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(): Flow<Resource<GetChatRooms?>> = flow {
        var response: GetChatRooms? = null
        emit(Resource.Loading<GetChatRooms?>())
        try {
            response = repository.getChatRooms()
            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error<GetChatRooms?>(data = response, message = e.toString()))
        }
    }
}