package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.GetUserInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserInfoUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(): Flow<Resource<GetUserInfoDto>> = flow {
        emit(Resource.Loading())
        try {
           val response = repository.getUserInfo()
            if (response != null) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error("response is null"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }
}