package com.abouttime.blindcafe.domain.use_case.remote.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.remote.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostUserInfoUseCase(
    private val repository: UserInfoRepository,
) {
    operator fun invoke(postUserInfoDto: PostUserInfoDto): Flow<Resource<BaseResponse>> = flow {
        var response: BaseResponse? = null
        emit(Resource.Loading<BaseResponse>())
        try {
            response = repository.postUserInfo(postUserInfoDto = postUserInfoDto)
            if (response != null) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error<BaseResponse>("response is null"))
            }
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

