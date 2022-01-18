package com.abouttime.blindcafe.domain.use_case.remote.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.remote.server.dto.user_info.partner.GetPartnerProfileDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetPartnerProfileUseCase(
    private val repository: UserInfoRepository
) {
    operator fun invoke(matchingId: Int): Flow<Resource<GetPartnerProfileDto?>> = flow {
        emit(Resource.Loading<GetPartnerProfileDto?>())
        try {
            val response = repository.getPartnerProfile(matchingId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error<GetPartnerProfileDto?>(message = message.code.toString()))
            } else {
               // emit(Resource.Error<GetPartnerProfileDto?>(message = e.message.toString()))
            }
        }
    }
}