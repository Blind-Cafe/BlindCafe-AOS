package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.user_info.partner.matched.GetMatchingProfileDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetMatchingProfileUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<GetMatchingProfileDto?>> = flow {
        emit(Resource.Loading<GetMatchingProfileDto?>())
        try {
            val response = repository.getMatchingProfile(userId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error<GetMatchingProfileDto?>(message = message.code.toString()))
            } else {
                //emit(Resource.Error<GetMatchingProfileDto?>(message = e.message.toString()))
            }
        }

    }
}