package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.home.GetHomeInfoDto
import com.abouttime.blindcafe.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHomeInfoUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<Resource<GetHomeInfoDto>> = flow {
        emit(Resource.Loading<GetHomeInfoDto>())
        try {
            val response = repository.getHomeInfo()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error<GetHomeInfoDto>(e.toString()))
        }
    }
}