package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.domain.repository.MatchingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class DeleteDismissMatchingUseCase(
    private val repository: MatchingRepository
) {
    operator fun invoke(matchingId: Int, reason: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading<Unit>())
        try {
            repository.deleteDismissMatching(matchingId = matchingId, reason = reason)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error<Unit>(message = message.code.toString()))
            } else {
                emit(Resource.Error<Unit>(message = e.message.toString()))
            }
        }
    }
}