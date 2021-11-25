package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.matching.GetChatRoomsDto
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetChatRoomsUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(): Flow<Resource<GetChatRoomsDto?>> = flow {
        var response: GetChatRoomsDto? = null
        emit(Resource.Loading<GetChatRoomsDto?>())
        try {
            response = repository.getChatRooms()
            emit(Resource.Success(response))

        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            }
        }
    }
}