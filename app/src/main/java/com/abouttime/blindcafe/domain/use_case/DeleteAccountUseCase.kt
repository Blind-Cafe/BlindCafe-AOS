package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAccountUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(reason: Int): Flow<Resource<DeleteAccountResponse?>> = flow {
        emit(Resource.Loading<DeleteAccountResponse?>())
        try {
            val response = repository.deleteAccount(reason)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error<DeleteAccountResponse?>(message = e.toString()))
        }
    }
}