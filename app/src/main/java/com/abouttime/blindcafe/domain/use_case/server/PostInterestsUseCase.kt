package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.interest.PostInterestDto
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenResponse
import com.abouttime.blindcafe.domain.repository.InterestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostInterestsUseCase(
    private val repository: InterestRepository
) {
    operator fun invoke(postInterestDto: PostInterestDto): Flow<Resource<BaseResponse?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.postInterests(postInterestDto)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            } else {
                emit(Resource.Error<BaseResponse?>("예상치 못한 에러발생\n $e"))
            }

        }
    }
}