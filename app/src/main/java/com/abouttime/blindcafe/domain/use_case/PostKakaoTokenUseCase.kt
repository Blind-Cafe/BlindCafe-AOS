package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.KakaoTokenDto
import com.abouttime.blindcafe.data.server.dto.KakaoTokenResponse
import com.abouttime.blindcafe.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostKakaoTokenUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(kakaoTokenDto: KakaoTokenDto): Flow<Resource<KakaoTokenResponse>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.postKakaoAccessToken(kakaoTokenDto)
            if (response != null) {
                emit(Resource.Success(data = response))
            } else {
                emit(Resource.Error("response is null"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("에러발생\n $e"))
        }
    }
}