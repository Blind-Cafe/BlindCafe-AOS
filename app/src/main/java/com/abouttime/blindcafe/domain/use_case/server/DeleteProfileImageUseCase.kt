package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class DeleteProfileImageUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(priority: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            repository.deleteProfileImage(priority)
            emit(Resource.Success(data = Unit))
        } catch(e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.code.toString()))
            } else {
                emit(Resource.Error(message = e.toString()))
            }
        }
    }
}