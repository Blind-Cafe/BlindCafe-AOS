package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.user_info.report.GetReportsDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetReportsUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(): Flow<Resource<GetReportsDto?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getReports()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.toString()))
            }
        }
    }
}