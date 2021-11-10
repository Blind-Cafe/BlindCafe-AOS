package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.server.api.LoginApi
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenResponse
import com.abouttime.blindcafe.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginApi: LoginApi): LoginRepository {
    override suspend fun postKakaoAccessToken(kakaoTokenDto: KakaoTokenDto): KakaoTokenResponse? {
        return loginApi.postKakaoAccessToken(kakaoAccessTokenDto = kakaoTokenDto)
    }
}