package com.abouttime.blindcafe.domain.use_case.remote.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.remote.server.dto.matching.PostDrinkDto
import com.abouttime.blindcafe.data.remote.server.dto.matching.PostDrinkResponse
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostDrinkUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(matchingId: Int, drink: PostDrinkDto): Flow<Resource<PostDrinkResponse?>> = flow {

        emit(Resource.Loading<PostDrinkResponse?>())
        try {
            val response = repository.postDrink(matchingId, drink)
            emit(Resource.Success(data = response))
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