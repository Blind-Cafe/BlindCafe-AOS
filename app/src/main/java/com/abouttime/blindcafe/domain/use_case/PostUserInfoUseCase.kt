package com.abouttime.blindcafe.domain.use_case

import android.util.Log
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostUserInfoUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(postUserInfoDto: PostUserInfoDto): Flow<Resource<BaseResponse>> = flow {
        emit(Resource.Loading<BaseResponse>())
        try {
            val response = repository.postUserInfo(postUserInfoDto = postUserInfoDto)
            if (response != null) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error<BaseResponse>("response is null"))
            }
        } catch(e: Exception) {
            Log.e(LogTag.USER_INFO_TAG, e.toString())
            emit(Resource.Error<BaseResponse>(message = e.toString()))
        }
    }
}