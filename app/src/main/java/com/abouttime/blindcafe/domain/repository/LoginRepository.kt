package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.KakaoToken
import com.abouttime.blindcafe.data.server.dto.KakaoTokenResponse

interface LoginRepository {
    suspend fun postKakaoAccessToken(kakaoToken: KakaoToken): KakaoTokenResponse
}