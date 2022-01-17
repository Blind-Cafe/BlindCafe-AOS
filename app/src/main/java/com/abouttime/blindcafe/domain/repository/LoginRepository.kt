package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.remote.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.data.remote.server.dto.login.KakaoTokenResponse

interface LoginRepository {
    suspend fun postKakaoAccessToken(kakaoTokenDto: KakaoTokenDto): KakaoTokenResponse?
}