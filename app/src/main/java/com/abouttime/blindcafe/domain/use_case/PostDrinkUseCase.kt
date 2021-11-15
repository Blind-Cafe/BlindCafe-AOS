package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkResponse
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostDrinkUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(matchingId: Int): Flow<Resource<PostDrinkResponse?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.postDrink(matchingId)
            emit(Resource.Success(data = response))

        } catch (e: Exception) {
            emit(Resource.Error(message = e.toString()))
        }
    }
}