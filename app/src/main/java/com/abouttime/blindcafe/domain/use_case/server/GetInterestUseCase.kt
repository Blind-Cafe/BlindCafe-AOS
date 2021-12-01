package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.interest.GetInterestResponse
import com.abouttime.blindcafe.domain.repository.InterestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetInterestUseCase(
    private val repository: InterestRepository
) {
    operator fun invoke(id1: Int, id2:Int, id3: Int): Flow<Resource<GetInterestResponse?>> = flow {
        emit(Resource.Loading<GetInterestResponse?>())
        try {
            val response = repository.getInterests(id1, id2, id3)
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