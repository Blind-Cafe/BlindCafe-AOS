package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.remote.LoginApi
import com.abouttime.blindcafe.data.remote.dto.KakaoToken
import com.abouttime.blindcafe.data.remote.dto.KakaoTokenResponse
import com.abouttime.blindcafe.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginApi: LoginApi): LoginRepository {
    override suspend fun postKakaoAccessToken(kakaoToken: KakaoToken): KakaoTokenResponse {
        return loginApi.postKakaoAccessToken(kakaoToken)
    }
}