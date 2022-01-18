package com.abouttime.blindcafe.domain.use_case.remote.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class PatchProfileImageUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(priority: RequestBody, image: MultipartBody.Part?): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            repository.patchProfileImage(priority, image)
            emit(Resource.Success(data = Unit))
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