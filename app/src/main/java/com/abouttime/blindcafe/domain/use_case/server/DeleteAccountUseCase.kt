package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class DeleteAccountUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(reason: Int): Flow<Resource<DeleteAccountResponse?>> = flow {
        emit(Resource.Loading<DeleteAccountResponse?>())
        try {
            val response = repository.deleteAccount(reason)
            emit(Resource.Success(response))
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