package com.abouttime.blindcafe.domain.use_case.remote.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.remote.server.dto.matching.PostMatchingRequestResponse
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostMatchingRequestUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(): Flow<Resource<PostMatchingRequestResponse?>> = flow {
        emit(Resource.Loading<PostMatchingRequestResponse?>())
        try {
            val response = repository.postMatchingRequest()
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