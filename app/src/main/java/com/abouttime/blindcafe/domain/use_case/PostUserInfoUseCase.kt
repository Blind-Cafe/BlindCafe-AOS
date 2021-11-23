package com.abouttime.blindcafe.domain.use_case

import android.util.Log
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
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
                emit(Resource.Error<BaseResponse>(data = response, message = e.code().toString()))
                emit(Resource.Error<BaseResponse>(data = response,
                    message = e.response().toString()))
                emit(Resource.Error<BaseResponse>(data = response,
                    message = e.response()?.body().toString()))

                val jsonObject = JSONObject(e.response()?.errorBody().toString())
                emit(Resource.Error<BaseResponse>(data = response, message = "$jsonObject"))

            } else {
                Log.e(LogTag.USER_INFO_TAG, e.toString())
            }
        }
    }
}