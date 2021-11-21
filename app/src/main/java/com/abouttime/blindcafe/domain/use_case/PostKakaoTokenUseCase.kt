package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenResponse
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
            emit(Resource.Error<KakaoTokenResponse>("에러발생\n $e"))
        }
    }
}