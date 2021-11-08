package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.KakaoTokenDto
import com.abouttime.blindcafe.data.server.dto.KakaoTokenResponse

interface LoginRepository {
    suspend fun postKakaoAccessToken(kakaoTokenDto: KakaoTokenDto): KakaoTokenResponse?
}