package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostCancelMatchingUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(): Flow<Resource<BaseResponse?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.postCancelMatching()
            emit(Resource.Success<BaseResponse?>(BaseResponse()))
            //emit(Resource.Success(response))
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