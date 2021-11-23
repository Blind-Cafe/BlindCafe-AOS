package com.abouttime.blindcafe.domain.use_case

import android.util.Log
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
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
                emit(Resource.Error(message = message.toString()))
            }
        }
    }
}

