package com.abouttime.blindcafe.domain.use_case.server

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.ext.parseErrorBody
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenResponse
import com.abouttime.blindcafe.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PostKakaoTokenUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(kakaoTokenDto: KakaoTokenDto): Flow<Resource<KakaoTokenResponse>> = flow {
        try {
            emit(Resource.Loading<KakaoTokenResponse>())

            val response = repository.postKakaoAccessToken(kakaoTokenDto)
            if (response != null) {
                emit(Resource.Success(data = response))
            } else {
                emit(Resource.Error<KakaoTokenResponse>("response is null"))

            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val message = e.parseErrorBody()
                emit(Resource.Error(message = message.code.toString()))
            } else {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }
}

