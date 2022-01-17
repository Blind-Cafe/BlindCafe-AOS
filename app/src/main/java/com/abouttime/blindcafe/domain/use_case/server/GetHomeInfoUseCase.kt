package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.remote.server.dto.home.GetHomeInfoDto
import com.abouttime.blindcafe.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                emit(Resource.Error(message = message.code.toString()))
            } else {
                emit(Resource.Error(message = e.toString()))
            }
        }
    }
}



