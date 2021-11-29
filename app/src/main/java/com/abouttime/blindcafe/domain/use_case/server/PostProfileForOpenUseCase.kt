package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenResponse
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostProfileForOpenUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(matchingId: Int, postProfileForOpenDto: PostProfileForOpenDto): Flow<Resource<PostProfileForOpenResponse?>> = flow {
        emit(Resource.Loading<PostProfileForOpenResponse?>())
        try {
            val response = repository.postProfileForOpen(matchingId, postProfileForOpenDto)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error<PostProfileForOpenResponse?>(message = message.code.toString()))
            } else {
                emit(Resource.Error<PostProfileForOpenResponse?>(message = e.message.toString()))
            }
        }
    }
}