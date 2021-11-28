package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.user_info.profile.image.GetProfileImageDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.info.GetProfileInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Call
import retrofit2.HttpException

class GetProfileImageUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<GetProfileImageDto?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getProfileImage(userId)
            emit(Resource.Success(response))
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