package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostEnteringLogUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(matchingId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            repository.postEnteringLog(matchingId)
            emit(Resource.Success(data = Unit))
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