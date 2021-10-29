package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.remote.dto.KakaoToken
import com.abouttime.blindcafe.data.remote.dto.KakaoTokenResponse

interface LoginRepository {
    suspend fun postKakaoAccessToken(kakaoToken: KakaoToken): KakaoTokenResponse
}