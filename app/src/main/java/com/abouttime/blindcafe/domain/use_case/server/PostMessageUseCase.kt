package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostMessageUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(postMessageDto: PostMessageDto, matchingId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            repository.postMessage(postMessageDto = postMessageDto, matchingId = matchingId)
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.code.toString()))
            } else {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }
}