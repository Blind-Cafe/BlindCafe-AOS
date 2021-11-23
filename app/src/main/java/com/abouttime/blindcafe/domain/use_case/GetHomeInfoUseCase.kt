package com.abouttime.blindcafe.domain.use_case

import android.util.Log
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.home.GetHomeInfoDto
import com.abouttime.blindcafe.domain.repository.HomeRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class GetHomeInfoUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<Resource<GetHomeInfoDto>> = flow {
        emit(Resource.Loading<GetHomeInfoDto>())
        try {
            val response = repository.getHomeInfo()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error<GetHomeInfoDto>(message = message.toString()))
            }
            emit(Resource.Error<GetHomeInfoDto>(e.toString()))
        }
    }
}



