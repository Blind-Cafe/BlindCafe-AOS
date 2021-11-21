package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.matching.PostMatchingRequestResponse
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostMatchingRequestUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(): Flow<Resource<PostMatchingRequestResponse?>> = flow {
        emit(Resource.Loading<PostMatchingRequestResponse?>())
        try {
            val response = repository.postMatchingRequest()
            emit(Resource.Success(data = response))

        } catch (e: Exception) {
            emit(Resource.Error<PostMatchingRequestResponse?>(message = e.toString()))
        }
    }
}