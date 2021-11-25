package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetUserInfoUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(): Flow<Resource<GetUserInfoDto>> = flow {
        emit(Resource.Loading<GetUserInfoDto>())
        try {
           val response = repository.getUserInfo()
            if (response != null) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error<GetUserInfoDto>("response is null"))
            }

        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            }
        }
    }
}