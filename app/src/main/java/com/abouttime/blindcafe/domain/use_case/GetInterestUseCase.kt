package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.interest.GetInterestResponse
import com.abouttime.blindcafe.domain.repository.InterestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetInterestUseCase(
    private val repository: InterestRepository
) {
    operator fun invoke(id1: Int, id2:Int, id3: Int): Flow<Resource<GetInterestResponse?>> = flow {
        emit(Resource.Loading<GetInterestResponse?>())
        try {
            val response = repository.getInterests(id1, id2, id3)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error<GetInterestResponse?>(message = e.toString()))
        }
    }
}