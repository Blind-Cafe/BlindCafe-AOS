package com.abouttime.blindcafe.domain.use_case.remote.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.image.GetProfileImageDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetProfileImageUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<GetProfileImageDto?>> = flow {
        emit(Resource.Loading<GetProfileImageDto?>())
        try {
            val response = repository.getProfileImage(userId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error<GetProfileImageDto?>(message = message.code.toString()))
            } else {
                emit(Resource.Error<GetProfileImageDto?>(message = e.message.toString()))
            }
        }
    }
}