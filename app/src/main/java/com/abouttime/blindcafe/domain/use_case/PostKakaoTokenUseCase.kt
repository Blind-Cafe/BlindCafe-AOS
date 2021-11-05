package com.abouttime.blindcafe.domain.use_case

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.server.dto.KakaoToken
import com.abouttime.blindcafe.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostKakaoTokenUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(kakaoToken: KakaoToken): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.postKakaoAccessToken(kakaoToken)
            val jwt = response.jwt
            emit(Resource.Success(jwt ?: "jwt is null"))
        } catch (e: Exception) {
            emit(Resource.Error("에러발생\n ${e.printStackTrace()}"))
        }
    }
}