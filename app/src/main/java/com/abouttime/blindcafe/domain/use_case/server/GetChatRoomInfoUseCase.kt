package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.matching.GetChatRoomInfoDto
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetChatRoomInfoUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(matchingId: Int): Flow<Resource<GetChatRoomInfoDto?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getChatRoomInfo(matchingId)
            emit(Resource.Success(data = response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.code.toString()))
            } else {
                emit(Resource.Error(message = e.toString()))
            }
        }
    }
}