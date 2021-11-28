package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.http.Part

class PatchProfileImageUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(priority: RequestBody, image: MultipartBody.Part?): Flow<Resource<Call<Unit>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.patchProfileImage(priority, image)
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